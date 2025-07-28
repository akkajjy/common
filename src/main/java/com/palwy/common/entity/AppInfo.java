package com.palwy.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel(description = "应用基础信息实体")
public class AppInfo {
    @ApiModelProperty("自增ID")
    private Long id;

    @ApiModelProperty(value = "应用名称", required = true)
    private String appName;

    @ApiModelProperty("应用类型(1:商城 2:助贷)")
    private String appType;

    @ApiModelProperty(value = "操作系统类型(1:Android 2:iOS)", required = true)
    private String osType;

    @ApiModelProperty("发版平台(如华为/AppStore)")
    private String platform;

    @ApiModelProperty("附加信息")
    private String extraInfo;

    @ApiModelProperty("删除标识(N-未删除；Y-已删除)")
    private String isDeleted = "N";

    @ApiModelProperty("创建人")
    private String creator = "system";

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("更新人")
    private String modifier = "system";

    @ApiModelProperty("更新时间")
    private Date gmtModified;

    @ApiModelProperty("版本号")
    private String versionCode;

    @ApiModelProperty("版本名称")
    private String versionName;

    @ApiModelProperty("下载地址")
    private String downloadUrl;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("渠道")
    private String channel;

    @ApiModelProperty("设备过滤")
    private String deviceFilter;

    @ApiModelProperty("是否展示现金贷(0:否 1:是)")
    private Boolean showCashLoan;
}
