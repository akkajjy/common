package com.palwy.common.service.impl;

import com.palwy.common.entity.AppInfoDO;
import com.palwy.common.mapper.AppInfoDOMapper;
import com.palwy.common.service.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Autowired
    private AppInfoDOMapper appInfoDOMapper;

    @Override
    public List<AppInfoDO> getAllAppInfos() {
        return appInfoDOMapper.getAllAppInfos();
    }

    @Override
    public int addAppInfo(AppInfoDO appInfoDO) {
        return appInfoDOMapper.saveAppInfo(appInfoDO);
    }
}
