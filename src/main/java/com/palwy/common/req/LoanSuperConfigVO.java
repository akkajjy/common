package com.palwy.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class LoanSuperConfigVO {
    private Long id;

    @ApiModelProperty("产品编号")
    private String prdCode;

    @ApiModelProperty("产品名称")
    private String prdName;

    @ApiModelProperty("产品标签")
    private String prdTag;

    @ApiModelProperty("产品logo")
    private String prdLogo;

    @ApiModelProperty("产品logo访问url")
    private String prdLogoAccessUrl;

    @ApiModelProperty("产品状态 ENABLE:上架 DISABLE:下架")
    private String prdStatus;

    @ApiModelProperty("金额上限")
    private BigDecimal amtUpper;

    @ApiModelProperty("展示时间开始")
    private String showTimeStart;

    @ApiModelProperty("展示时间结束")
    private String showTimeEnd;

    @ApiModelProperty("节假日是否展示 Y:展示 N:不展示")
    private String showHoliday;

    @ApiModelProperty("展示顺序")
    private String showOrder;

    @ApiModelProperty("跳转链接")
    private String linkUrl;

    private String isDeleted;

    private String creator;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    private String modifier;

    @ApiModelProperty("更新时间")
    private Date gmtModified;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty("链接是否展示 Y:展示 N:不展示")
    private String showLink;
}