package com.palwy.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "应用更新管理实体")
public class AppUpdateManage {
    @ApiModelProperty("自增ID")
    private Long id;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("应用ID")
    private Long appId;

    @ApiModelProperty("版本号")
    private String versionCode;

    @ApiModelProperty("版本名称")
    private String versionName;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("强制更新类型(0:不更新 1:不强制更新 2:强制更新)")
    private String forceUpdateType = "0"; // 默认不更新

    @ApiModelProperty("最低支持版本号")
    private String lowVersionCode;

    @ApiModelProperty("更新描述")
    private String updateDesc;

    @ApiModelProperty("创建人")
    private String creator = "system";

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("更新人")
    private String modifier = "system";

    @ApiModelProperty("更新时间")
    private Date gmtModified;

    @ApiModelProperty("删除标识(N-未删除；Y-已删除)")
    private String isDeleted = "N";
}
