package com.el.altria.dto;

import lombok.Data;

@Data
public class FoodTruckQueryDTO  extends BaseQueryDTO{
    private String fuzzySearchMenu;
    private Float distanceInKM;
    private Double latitude;
    private Double longitude;
}
