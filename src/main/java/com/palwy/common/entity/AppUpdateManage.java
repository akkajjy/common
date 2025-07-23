package com.palwy.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class AppUpdateManage implements Serializable {
    @ApiModelProperty("主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("应用名称")
    @NotBlank(message = "应用名称不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String appName;

    @ApiModelProperty("版本号")
    @NotBlank(message = "版本号不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String versionCode;

    @ApiModelProperty("版本名称")
    @NotBlank(message = "版本名称不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String versionName;

    @ApiModelProperty("平台")
    @NotBlank(message = "发版平台不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String platform;

    @ApiModelProperty("强制更新类型(0:不更新 1:不强制更新 2:强制更新)")
    @NotBlank(message = "强制更新类型不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String forceUpdateType;

    @ApiModelProperty("最低支持版本号")
    @NotBlank(message = "最低支持版本号不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String lowVersionCode;

    @ApiModelProperty("更新描述")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String updateDesc;

    @ApiModelProperty("创建人")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String creator;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreated;

    @ApiModelProperty("修改人")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modifier;

    @ApiModelProperty("修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    @ApiModelProperty("删除标识(N:未删除 Y:已删除)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String isDeleted;

    @ApiModelProperty("系统类型(1:Android 2:iOS)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String osType;
}
