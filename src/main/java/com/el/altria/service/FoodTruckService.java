package com.el.altria.service;

import com.el.altria.dto.BaseResponse;
import com.el.altria.dto.FoodTruckDTO;
import com.el.altria.dto.FoodTruckQueryDTO;

import java.util.List;

public interface FoodTruckService {
    void initData();

    BaseResponse<List<FoodTruckDTO>> getNearbyChoicesOfCertainMenu(FoodTruckQueryDTO params);

    BaseResponse<FoodTruckDTO> getRandomNearbyChoice(FoodTruckQueryDTO params);
}
