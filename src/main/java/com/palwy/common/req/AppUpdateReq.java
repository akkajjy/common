package com.palwy.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppUpdateReq {
    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("应用ID")
    private Long appId;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("强制更新类型(0/1/2)")
    private String forceUpdateType;

    @ApiModelProperty("版本号")
    private String versionCode;

    @ApiModelProperty("页码，默认1")
    private Integer pageNum = 1;

    @ApiModelProperty("每页数量，默认10")
    private Integer pageSize = 10;
}
