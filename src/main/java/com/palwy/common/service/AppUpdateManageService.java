package com.palwy.common.service;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;

public interface AppUpdateManageService {
    int create(AppUpdateManage record);
    int update(AppUpdateManage record);
    int delete(Integer id);
    AppUpdateManage getById(Integer id);
    PageInfo<AppUpdateManage> listByPage(int pageNum, int pageSize,
                                         String appName, String osType);
    AppUpdateManage checkForceUpdate(String osType, String versionCode, String platform);
}
