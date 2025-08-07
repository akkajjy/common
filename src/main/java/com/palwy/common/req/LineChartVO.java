package com.palwy.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LineChartVO {

    @ApiModelProperty("下标数组")
    private List<String> indexList;

    @ApiModelProperty("value数组")
    private List<BigDecimal> valueList;

}
