package com.palwy.common.service;

import com.palwy.common.entity.AppInfoDO;

import java.util.List;

public interface AppInfoService {
    List<AppInfoDO> getAllAppInfos();

    int addAppInfo(AppInfoDO appInfoDO);
}
