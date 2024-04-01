package com.el.altria.controller;

import com.el.altria.dto.BaseResponse;
import com.el.altria.dto.FoodTruckDTO;
import com.el.altria.dto.FoodTruckQueryDTO;
import com.el.altria.service.FoodTruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/foodTruck")
public class FoodTruckController {

    final
    private FoodTruckService foodTruckService;

    public FoodTruckController(FoodTruckService foodTruckService) {
        this.foodTruckService = foodTruckService;
    }

    @PostMapping("/init")
    public ResponseEntity<BaseResponse<Void>> initData() {
        foodTruckService.initData();
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @GetMapping("/nearbyChoices")
    public ResponseEntity<BaseResponse<List<FoodTruckDTO>>> getNearbyChoicesOfCertainMenu(FoodTruckQueryDTO params) {
        return ResponseEntity.ok(foodTruckService.getNearbyChoicesOfCertainMenu(params));
    }

    @GetMapping("/nearbyChoice/random")
    public ResponseEntity<BaseResponse<FoodTruckDTO>> getRandomNearbyChoice(FoodTruckQueryDTO params) {
        return ResponseEntity.ok(foodTruckService.getRandomNearbyChoice(params));
    }




}
