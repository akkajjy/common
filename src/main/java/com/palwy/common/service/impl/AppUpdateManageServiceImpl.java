package com.palwy.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.mapper.AppUpdateManageMapper;
import com.palwy.common.req.AppUpdateManageReq;
import com.palwy.common.service.AppUpdateManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUpdateManageServiceImpl implements AppUpdateManageService {
    @Autowired
    private AppUpdateManageMapper mapper;

    @Override
    public int create(AppUpdateManageReq record) {
        String platformString = String.join(",", record.getPlatform());
        AppUpdateManage appUpdateManage = new AppUpdateManage();
        appUpdateManage.setAppName(record.getAppName());
        appUpdateManage.setVersionCode(record.getVersionCode());
        appUpdateManage.setVersionName(record.getVersionName());
        appUpdateManage.setPlatform(platformString);
        appUpdateManage.setForceUpdateType(record.getForceUpdateType());
        appUpdateManage.setLowVersionCode(record.getLowVersionCode());
        appUpdateManage.setUpdateDesc(record.getUpdateDesc());
        appUpdateManage.setOsType(record.getOsType());
        return mapper.insert(appUpdateManage);
    }

    @Override
    public int update(AppUpdateManageReq record) {
        String platformString = String.join(",", record.getPlatform());
        AppUpdateManage appUpdateManage = new AppUpdateManage();
        appUpdateManage.setAppName(record.getAppName());
        appUpdateManage.setVersionCode(record.getVersionCode());
        appUpdateManage.setVersionName(record.getVersionName());
        appUpdateManage.setPlatform(platformString);
        appUpdateManage.setForceUpdateType(record.getForceUpdateType());
        appUpdateManage.setLowVersionCode(record.getLowVersionCode());
        appUpdateManage.setUpdateDesc(record.getUpdateDesc());
        appUpdateManage.setOsType(record.getOsType());
        return mapper.update(appUpdateManage);
    }

    @Override
    public int delete(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public AppUpdateManage getById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public PageInfo<AppUpdateManage> listByPage(int pageNum, int pageSize,
                                                String appName, String osType) {
        PageHelper.startPage(pageNum, pageSize);
        List<AppUpdateManage> list = mapper.selectByPage(appName, osType);
        PageInfo<AppUpdateManage> pageList = new PageInfo<>(list);
        pageList.setTotal(list.size());
        return pageList;
    }

    @Override
    public AppUpdateManage checkForceUpdate(String osType, String versionCode, String platform) {
        return mapper.selectForceUpdate(osType, versionCode, platform);
    }
}
