package com.el.altria.constant;

import com.el.altria.enums.CsvColumnTypeEnum;

import java.util.LinkedHashMap;

public class CsvUtilConstant {
    public final static LinkedHashMap<String, Integer> FOOD_TRUCK_COLUMN_MAP;

    static {
        LinkedHashMap<String, Integer> columnMap = new LinkedHashMap<>();
        columnMap.put("locationId", CsvColumnTypeEnum.INTEGER.getType());
        columnMap.put("applicant", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("facilityType", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("cnn", CsvColumnTypeEnum.INTEGER.getType());
        columnMap.put("locationDescription", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("address", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("blockLot", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("block", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("lot", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("permit", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("status", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("foodItems", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("x", CsvColumnTypeEnum.DOUBLE.getType());
        columnMap.put("y", CsvColumnTypeEnum.DOUBLE.getType());
        columnMap.put("latitude", CsvColumnTypeEnum.DOUBLE.getType());
        columnMap.put("longitude", CsvColumnTypeEnum.DOUBLE.getType());
        columnMap.put("schedule", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("dayshours", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("noiSent", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("approved", CsvColumnTypeEnum.DATE.getType());
        columnMap.put("received", CsvColumnTypeEnum.INTEGER.getType());
        columnMap.put("priorPermit", CsvColumnTypeEnum.INTEGER.getType());
        columnMap.put("expirationDate", CsvColumnTypeEnum.DATE.getType());
        columnMap.put("location", CsvColumnTypeEnum.STRING.getType());
        columnMap.put("firePreventionDistricts", CsvColumnTypeEnum.INTEGER.getType());
        columnMap.put("policeDistricts", CsvColumnTypeEnum.INTEGER.getType());
        columnMap.put("supervisorDistricts", CsvColumnTypeEnum.INTEGER.getType());
        columnMap.put("zipCodes", CsvColumnTypeEnum.INTEGER.getType());
        columnMap.put("neighborhoods", CsvColumnTypeEnum.INTEGER.getType());
        FOOD_TRUCK_COLUMN_MAP = columnMap;
    }


}
