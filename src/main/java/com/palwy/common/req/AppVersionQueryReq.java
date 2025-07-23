package com.palwy.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AppVersionQueryReq {
    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty(value = "操作系统类型: IOS/ANDROID", example = "ANDROID")
    private String osType;

    @ApiModelProperty("渠道列表")
    private List<String> channels;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer pageSize = 10;
}
