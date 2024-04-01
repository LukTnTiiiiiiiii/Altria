package com.el.altria.entity;

import com.el.altria.dto.FoodTruckDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class FoodTruckEntity {
    private Integer locationId;
    private String applicant;
    private String facilityType;
    private Integer cnn;
    private String locationDescription;
    private String address;
    private String blockLot;
    private String block;
    private String lot;
    private String permit;
    private String status;
    private String foodItems;
    private Double x;
    private Double y;
    private Double latitude;
    private Double longitude;
    private String schedule;
    private String dayshours;
    private String noiSent;
    private Date approved;
    private Integer received;
    private Integer priorPermit;
    private Date expirationDate;
    private String location;
    private Integer firePreventionDistricts;
    private Integer policeDistricts;
    private Integer supervisorDistricts;
    private Integer zipCodes;
    private Integer neighborhoods;

    public FoodTruckEsEntity toEsEntity() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        java.util.Date date = null;
        try {
            date = sdf.parse(this.received.toString());
        } catch (ParseException e) {
            log.error("Failed to parse date");
        }
        return FoodTruckEsEntity.builder()
                .locationId(this.locationId)
                .locationDescription(this.locationDescription)
                .address(this.address)
                .status(this.status)
                .foodItems(this.foodItems)
                .received(date)
                .geoPoint(new GeoPoint(this.latitude,this.longitude))
                .build();
    }

    public FoodTruckDTO toDTO() {
        return FoodTruckDTO.builder()
                .locationId(this.locationId)
                .applicant(this.applicant)
                .facilityType(this.facilityType)
                .cnn(this.cnn)
                .locationDescription(this.locationDescription)
                .address(this.address)
                .blockLot(this.blockLot)
                .block(this.block)
                .lot(this.lot)
                .permit(this.permit)
                .status(this.status)
                .foodItems(this.foodItems)
                .x(this.x)
                .y(this.y)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .schedule(this.schedule)
                .dayshours(this.dayshours)
                .noiSent(this.noiSent)
                .approved(this.approved)
                .received(this.received)
                .priorPermit(this.priorPermit)
                .expirationDate(this.expirationDate)
                .location(this.location)
                .firePreventionDistricts(this.firePreventionDistricts)
                .policeDistricts(this.policeDistricts)
                .supervisorDistricts(this.supervisorDistricts)
                .zipCodes(this.zipCodes)
                .neighborhoods(this.neighborhoods)
                .build();
    }
}
