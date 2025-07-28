package com.palwy.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Data
public class AppVersionQueryReq {
    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty(value = "操作系统类型: IOS/ANDROID", example = "ANDROID")
    private String osType;

    @ApiModelProperty("渠道列表")
    private String channel;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer pageSize = 10;
    @ApiModelProperty("版本号")
    private String versionCode;
}
