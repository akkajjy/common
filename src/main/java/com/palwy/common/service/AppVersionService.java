package com.palwy.common.service;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppInfoDO;
import com.palwy.common.entity.AppVersionDO;
import com.palwy.common.req.AppInfoReq;
import com.palwy.common.resp.AppVersionResp;

import java.util.List;

public interface AppVersionService {
    List<AppVersionDO> getAllAppVersions();
    AppVersionDO getAppVersionById(Long id);
    int saveAppVersion(AppInfoDO appInfoDO, AppInfoReq appInfoReq);
    int updateAppVersion(AppVersionDO appVersion);
    int deleteAppVersion(Long id, String modifier);

    int deleteVersionsByAppId(Long appId);
    int saveBatchAppVersion(List<AppVersionDO> versions);

    PageInfo<AppVersionResp> listAppVersionsByCondition(
            String appName,
            String osType,
            List<String> channels,
            int pageNum,
            int pageSize);
}
