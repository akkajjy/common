package com.palwy.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.mapper.AppUpdateManageMapper;
import com.palwy.common.service.AppUpdateManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUpdateManageServiceImpl implements AppUpdateManageService {
    @Autowired
    private AppUpdateManageMapper mapper;

    @Override
    public int create(AppUpdateManage record) {
        return mapper.insert(record);
    }

    @Override
    public int update(AppUpdateManage record) {
        return mapper.update(record);
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
        return new PageInfo<>(list);
    }

    @Override
    public AppUpdateManage checkForceUpdate(String osType, String versionCode, String platform) {
        return mapper.selectForceUpdate(osType, versionCode, platform);
    }
}
