package com.palwy.common.service;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.req.AppUpdateManageReq;

public interface AppUpdateManageService {
    int create(AppUpdateManageReq record);
    int update(AppUpdateManageReq record);
    int delete(Integer id);
    AppUpdateManage getById(Integer id);
    PageInfo<AppUpdateManage> listByPage(int pageNum, int pageSize,
                                         String appName, String osType);
    AppUpdateManage checkForceUpdate(String osType, String versionCode, String platform);
}
