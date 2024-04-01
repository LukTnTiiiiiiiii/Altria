package com.el.altria.entity;

import com.el.altria.constant.AltriaContants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = AltriaContants.FOOD_TRUCK_INDEX_NAME)
public class FoodTruckEsEntity {
    @Field(type = FieldType.Integer)
    private Integer locationId;

    @Field(type = FieldType.Text)
    private String locationDescription;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Text)
    private String foodItems;

    @Field(type = FieldType.Date)
    private Date received;

    @GeoPointField
    private GeoPoint geoPoint;
}
