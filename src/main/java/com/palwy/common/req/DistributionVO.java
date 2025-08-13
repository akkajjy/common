package com.palwy.common.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DistributionVO {

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("值")
    private BigDecimal value;

    @ApiModelProperty("x轴下标")
    @JsonProperty("xIndex")
    private String xIndex;

    @ApiModelProperty("排名")
    private String order;

    @ApiModelProperty("值")
    private BigDecimal percentage;


}
