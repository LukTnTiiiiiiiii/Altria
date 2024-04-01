package com.el.altria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodTruckDTO {
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

}
