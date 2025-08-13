package com.palwy.common.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryDataVO {

    private String data_label;

    private String index;

    private BigDecimal indexValue;

    private String data_time;

}
