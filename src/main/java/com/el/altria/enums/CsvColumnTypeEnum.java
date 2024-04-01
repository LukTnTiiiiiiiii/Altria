package com.el.altria.enums;

import com.el.altria.constant.AltriaContants;
import lombok.Data;

public enum CsvColumnTypeEnum {
    INTEGER(AltriaContants.CSV_COL_TYPE_INTEGER),
    DOUBLE(AltriaContants.CSV_COL_TYPE_DOUBLE),
    STRING(AltriaContants.CSV_COL_TYPE_STRING),
    DATE(AltriaContants.CSV_COL_TYPE_DATE);

    private Integer type;

    public Integer getType() {
        return type;
    }

    CsvColumnTypeEnum(Integer type) {
        this.type = type;
    }
}
