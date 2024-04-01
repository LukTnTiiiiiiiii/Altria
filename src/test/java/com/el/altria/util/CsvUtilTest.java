package com.el.altria.util;

import com.el.altria.constant.CsvUtilConstant;
import com.el.altria.entity.FoodTruckEntity;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class CsvUtilTest {



    @Test
    public void readTest() throws FileNotFoundException {
        String filePath = this.getClass().getClassLoader().getResource("data/Mobile_Food_Facility_Permit.csv").getPath();
        List<FoodTruckEntity> list = CsvUtil.read(new File(filePath), FoodTruckEntity.class, CsvUtilConstant.FOOD_TRUCK_COLUMN_MAP, "MM/dd/yyyy HH:mm:ss 'AM'");
        Assert.assertTrue(list!=null);
        Assert.assertTrue(list.size()==2);
        FoodTruckEntity firstRow = list.get(0);
        Assert.assertEquals(firstRow.getLocationId(),Integer.valueOf(735318));
        Assert.assertEquals(firstRow.getLocation(),"(37.794331003246846, -122.39581105302317)");

    }




}
