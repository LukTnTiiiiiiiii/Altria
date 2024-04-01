package com.el.altria.cache;

import com.el.altria.constant.CsvUtilConstant;
import com.el.altria.entity.FoodTruckEntity;
import com.el.altria.util.CsvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class FoodTruckRedisCache extends RedisCacheAbstract<Integer, FoodTruckEntity> {
    @Override
    public FoodTruckEntity supply(Integer key) {
        // Generally get data from MYSQL , but in this demo we use the CSV data file as MYSQL instead for convenience.
        supplyAll();
        return cache.get(key);
    }

    void supplyAll() {
        // Generally get data from MYSQL , but in this demo we use the CSV data file as MYSQL instead for convenience.
        String filePath = this.getClass().getClassLoader().getResource("data/Mobile_Food_Facility_Permit.csv").getPath();
        try {
            List<FoodTruckEntity> list = CsvUtil.read(new File(filePath), FoodTruckEntity.class, CsvUtilConstant.FOOD_TRUCK_COLUMN_MAP, "MM/dd/yyyy HH:mm:ss 'AM'");
            Optional.ofNullable(list)
                    .orElse(new ArrayList<>())
                    .stream()
                    .forEach(entity->cache.putIfAbsent(entity.getLocationId(),entity));
        } catch (FileNotFoundException e) {
            log.error("Failed to cache ", e);
        }
    }
}
