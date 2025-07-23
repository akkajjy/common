package com.palwy.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AppUpdateManage {
    @ApiModelProperty("主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("版本号")
    private String versionCode;

    @ApiModelProperty("版本名称")
    private String versionName;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("强制更新类型(0:不更新 1:不强制更新 2:强制更新)")
    private String forceUpdateType;

    @ApiModelProperty("最低支持版本号")
    private String lowVersionCode;

    @ApiModelProperty("更新描述")
    private String updateDesc;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreated;

    @ApiModelProperty("修改人")
    private String modifier;

    @ApiModelProperty("修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    @ApiModelProperty("删除标识(N:未删除 Y:已删除)")
    private String isDeleted;

    @ApiModelProperty("系统类型(1:Android 2:iOS)")
    private String osType;
}
