package com.palwy.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AppInfoReq {
    @ApiModelProperty("应用Id")
    private Long id;
    @ApiModelProperty("应用名称")
    @NotBlank(message = "应用名称不能为空")
    private String appName;
    @ApiModelProperty("应用类型1:商城 2:助贷 ...")
    @NotBlank(message = "应用类型不能为空")
    private String appType;
    @ApiModelProperty("平台类型1:Android 2:iOS")
    @NotBlank(message = "请选择Android或IOS")
    private String osType;
    @ApiModelProperty("附加信息")
    private String extraInfo;
    @ApiModelProperty("删除标识：N-未删除；Y-已删除")
    private String isDeleted;

    @NotBlank(message = "版本号不能为空")
    @ApiModelProperty("版本号(如1.0.0)")
    private String versionCode;
    @ApiModelProperty("版本别名(可选)")
    private String versionName;
    @ApiModelProperty("下载地址")
    private String downloadUrl;
    @ApiModelProperty("上传文件地址")
    private String filePath;
    @ApiModelProperty("发版平台(如应用宝)")
    private List<String> channelList;
    @ApiModelProperty("设备信息过滤(JSON)")
    private String deviceFilter;
    @ApiModelProperty("是否展示现金贷(0:否 1:是)")
    private Integer showCashLoan;
    @ApiModelProperty("强制更新(0:否 1:是)")
    private Integer forceUpdateType;
    @ApiModelProperty("更新描述")
    private String updateDesc;
    @ApiModelProperty("删除标识：N-未删除；Y-已删除")
    private String isVersionDeleted;
}
