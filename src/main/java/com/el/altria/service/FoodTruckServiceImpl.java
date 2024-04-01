package com.el.altria.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.ResponseBody;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.el.altria.cache.FoodTruckRedisCache;
import com.el.altria.constant.AltriaContants;
import com.el.altria.constant.CsvUtilConstant;
import com.el.altria.dto.BaseResponse;
import com.el.altria.dto.FoodTruckDTO;
import com.el.altria.dto.FoodTruckQueryDTO;
import com.el.altria.entity.FoodTruckEntity;
import com.el.altria.entity.FoodTruckEsEntity;
import com.el.altria.util.CsvUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class FoodTruckServiceImpl implements FoodTruckService {

    private final ElasticsearchClient esClient;

    private final FoodTruckRedisCache foodTruckRedisCache;

    public FoodTruckServiceImpl(ElasticsearchClient esClient, FoodTruckRedisCache foodTruckRedisCache) {
        this.esClient = esClient;
        this.foodTruckRedisCache = foodTruckRedisCache;
    }


    @Override
    public void initData() {
        try {
            String filePath = this.getClass().getClassLoader().getResource("data/Mobile_Food_Facility_Permit.csv").getPath();
            List<FoodTruckEntity> list = CsvUtil.read(new File(filePath), FoodTruckEntity.class, CsvUtilConstant.FOOD_TRUCK_COLUMN_MAP, "MM/dd/yyyy HH:mm:ss 'AM'");

            if (CollectionUtils.isEmpty(list)) {
                log.warn("No data to initialize food truck database");
                return;
            }


            BooleanResponse indexExist = esClient.indices()
                    .exists(f -> f.index(List.of(AltriaContants.FOOD_TRUCK_INDEX_NAME)));
            if (!indexExist.value()) {
                createIndex();
                for (FoodTruckEntity entity : list) {
                    esClient.index(
                            i -> i.index(AltriaContants.FOOD_TRUCK_INDEX_NAME)
                                    .document(entity.toEsEntity())
                    );
                }
            }
        } catch (IOException e) {
            log.error("Failed to initialize food truck database", e);
        }
    }

    private void createIndex() throws IOException {
        //create index with partial mapping because the type geo_point can not be mapped dynamically like others
        esClient.indices()
                .create(
                        c -> c.index(AltriaContants.FOOD_TRUCK_INDEX_NAME)
                                .withJson(new StringReader("\n" +
                                        "{\n" +
                                        "  \"mappings\": {\n" +
                                        "    \"properties\": {\n" +
                                        "      \"geoPoint\": { \"type\": \"geo_point\" }\n" +
                                        "    }\n" +
                                        "  }\n" +
                                        "}"))
                );
    }


    @Override
    public BaseResponse<List<FoodTruckDTO>> getNearbyChoicesOfCertainMenu(FoodTruckQueryDTO params) {
        if (StringUtils.isBlank(params.getFuzzySearchMenu())) {
            return BaseResponse.badRequest("fuzzySearchMenu is required");
        }

        // query Elasticsearch and get locationId
        SearchResponse<FoodTruckEsEntity> searchResponse = null;
        try {
            searchResponse = esClient.search(
                    s -> s.index(AltriaContants.FOOD_TRUCK_INDEX_NAME)
                            .query(
                                    q -> q.bool(
                                            b -> {
                                                b.must(
                                                        qq -> qq.match(
                                                                m -> m.field("foodItems")
                                                                        .query(params.getFuzzySearchMenu())
                                                        ));

                                                if (params.getDistanceInKM() != null && params.getLatitude() != null && params.getLongitude() != null) {
                                                    b.must(
                                                            qq -> qq.geoDistance(
                                                                    g -> g.distanceType(GeoDistanceType.Plane)
                                                                            .field("geoPoint")
                                                                            .distance(params.getDistanceInKM()+"km")
                                                                            .location(gp -> gp.latlon(ll -> ll.lat(params.getLatitude()).lon(params.getLongitude())))
                                                            ));
                                                }
                                                return b;
                                            }
                                    )
                            )
                            .from(params.getFrom())
                            .size(params.getSize())
                    , FoodTruckEsEntity.class);
        } catch (IOException e) {
            log.error("Failed to query ES", e);
            return BaseResponse.internalError(null);
        }

        // query full data from redis cache by locationId
        List<FoodTruckDTO> result = Optional.ofNullable(searchResponse)
                .map(ResponseBody::hits)
                .map(HitsMetadata::hits)
                .orElse(new ArrayList<>())
                .stream()
                .map(Hit::source)
                .map(t -> foodTruckRedisCache.get(t.getLocationId()))
                .filter(Objects::nonNull)
                .map(FoodTruckEntity::toDTO)
                .collect(Collectors.toList());
        return BaseResponse.ok(result);
    }


    @Override
    public BaseResponse<FoodTruckDTO> getRandomNearbyChoice(FoodTruckQueryDTO params) {
        if (params.getDistanceInKM() == null || params.getLatitude() == null || params.getLongitude() == null) {
            return BaseResponse.badRequest("longitude and latitude and distance are all required");
        }

        // query Elasticsearch and get locationId
        SearchResponse<FoodTruckEsEntity> searchResponse = null;
        try {
            searchResponse = esClient.search(
                    s -> s.index(AltriaContants.FOOD_TRUCK_INDEX_NAME)
                            .query(
                                    qq -> qq.geoDistance(
                                            g -> g.distanceType(GeoDistanceType.Plane)
                                                    .field("geoPoint")
                                                    .distance(params.getDistanceInKM() + "km")
                                                    .location(gp -> gp.latlon(ll -> ll.lat(params.getLatitude()).lon(params.getLongitude())))
                                    )
                            )
                            .from(0)
                            .size(1000)
                    , FoodTruckEsEntity.class);
        } catch (IOException e) {
            log.error("Failed to query ES", e);
            return BaseResponse.internalError(null);
        }

        List<Hit<FoodTruckEsEntity>> list = Optional.ofNullable(searchResponse)
                .map(ResponseBody::hits)
                .map(HitsMetadata::hits)
                .orElse(new ArrayList<>());

        int randomIdx = (int) (Math.random() * list.size());
        FoodTruckEntity entity = Optional.ofNullable(list.get(randomIdx))
                .map(Hit::source)
                .map(t -> foodTruckRedisCache.get(t.getLocationId()))
                .orElse(null);
        if (entity == null) return BaseResponse.ok(null);
        return BaseResponse.ok(entity.toDTO());
    }


}
