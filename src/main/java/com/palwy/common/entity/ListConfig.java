package com.palwy.common.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "平台版本配置")
public class ListConfig {
    private Long id;
    private String plateForm;
    private String version;
    private String showFlag;
    private String isDeleted;
    private String creator;
    private Date gmtCreated;
    private String modifier;
    private Date gmtModified;
}