package com.palwy.common.entity;

import lombok.Data;

@Data
public class AppVersionDO {
    private Long id;
    private Long appId;
    private String versionCode;
    private String versionName;
    private String downloadUrl;
    private String filePath;
    private String channel;
    private String deviceFilter;
    private Integer showCashLoan;
    private Integer forceUpdateType;
    private String updateDesc;
    private String isDeleted;
    private String creator;
    private String gmtCreated;
    private String modifier;
    private String gmtModified;
}
