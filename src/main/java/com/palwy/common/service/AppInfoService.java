package com.palwy.common.service;

import com.palwy.common.entity.AppInfoDO;
import com.palwy.common.req.AppInfoReq;
import com.palwy.common.vo.ResultVO;

import java.util.List;

public interface AppInfoService {
    List<AppInfoDO> getAllAppInfos();

    int addAppInfo(AppInfoDO appInfoDO);

    AppInfoDO getAppInfoById(Long id);

    int updateAppInfo(AppInfoDO appInfoDO);

    ResultVO addAppAndVersion(AppInfoReq appInfoReq);
    ResultVO updateAppAndVersion(AppInfoReq appInfoReq);
}
