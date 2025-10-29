package com.palwy.common.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel(description = "平台版本配置")
public class ListConfigReq {
    private String plateForm;
    private String version;
}