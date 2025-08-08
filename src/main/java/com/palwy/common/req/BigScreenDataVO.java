package com.palwy.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BigScreenDataVO {

    @ApiModelProperty("当日总金额")
    private String currentTotalAmt;

    @ApiModelProperty("当日订单量")
    private BigDecimal currentTotalCount;

    @ApiModelProperty("件均")
    private BigDecimal itemsAre;

    @ApiModelProperty("商城累计金额")
    private String shopAmt;

    @ApiModelProperty("权益累计金额")
    private String legalRightAmt;

    @ApiModelProperty("年龄分布")
    private List<DistributionVO> ageGroup;

    @ApiModelProperty("学历分布")
    private List<DistributionVO> enduGroup;

    @ApiModelProperty("地域分布")
    private List<DistributionVO> areaGroup;

    @ApiModelProperty("商城数据增长趋势")
    private LineChartVO shopDataGrowthTrend;

    @ApiModelProperty("商城数据增长趋势")
    private LineChartVO legalRightDataGrowthTrend;

    @ApiModelProperty("商城销售排行TOP10")
    private List<DistributionVO> shopSalesTop10;

}
