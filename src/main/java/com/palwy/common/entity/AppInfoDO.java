package com.palwy.common.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppInfoDO {
    private Long id;
    private String appName;
    private String appType;
    private String osType;
    private String platform;
    private String extraInfo;
    private String isDeleted;
    private String creator;
    private String gmtCreated;
    private String modifier;
    private String gmtModified;
}
