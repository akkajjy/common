package com.palwy.common.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AppVersionResp {
    @ApiModelProperty(value = "版本ID", example = "123")
    private Long id;

    @ApiModelProperty(value = "应用名称", example = "APP1")
    private String appName;

    @ApiModelProperty(value = "渠道标识", example = "小米")
    private String channel;

    @ApiModelProperty(value = "版本号", example = "1.0.0")
    private String versionCode;

    @ApiModelProperty(value = "版本名称", example = "正式版")
    private String versionName;

    @ApiModelProperty(value = "下载地址", example = "https://example.com/app.apk")
    private String downloadUrl;

    @ApiModelProperty(value = "是否显示现金贷", example = "Y")
    private String showCashLoan;

    @ApiModelProperty(value = "创建时间", example = "2023-05-15T08:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreated;
}
