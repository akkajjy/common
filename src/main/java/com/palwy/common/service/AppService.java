package com.palwy.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.mapper.AppInfoMapper;
import com.palwy.common.mapper.AppUpdateManageMapper;
import com.palwy.common.req.AppReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {
    @Autowired
    private AppInfoMapper appInfoMapper;
    @Autowired
    private AppUpdateManageMapper updateManageMapper;

    public List<AppInfo> getAllAppInfos() {
        return appInfoMapper.getAllAppInfos();
    }
    @Transactional
    public void createAppWithDefaultUpdate(AppInfo appInfo) {
        // 设置默认值
        appInfo.setCreator("system");
        appInfo.setModifier("system");

        // 插入AppInfo
        appInfoMapper.insertAppInfo(appInfo);

        // 创建默认的更新管理记录
        AppUpdateManage updateManage = new AppUpdateManage();
        updateManage.setAppName(appInfo.getAppName());
        updateManage.setAppId(appInfo.getId());
        updateManage.setVersionCode(appInfo.getVersionCode());
        updateManage.setVersionName(appInfo.getVersionName());
        updateManage.setPlatform(appInfo.getPlatform());
        updateManage.setForceUpdateType("0"); // 默认不更新
        updateManage.setCreator("system");
        updateManage.setModifier("system");

        updateManageMapper.insertAppUpdateManage(updateManage);
    }

    public PageInfo<AppInfo> getAppPage(AppReq appReq) {
        // 使用PageHelper实现分页
        // 设置分页参数
        PageHelper.startPage(appReq.getPageNum(), appReq.getPageSize());
        List<AppInfo> apps = appInfoMapper.selectAppInfoByCondition(appReq);
        PageInfo<AppInfo> pageInfo = new PageInfo<>(apps);
        pageInfo.setTotal(apps.size());
        return pageInfo;
    }

    @Transactional
    public void updateAppInfo(AppInfo appInfo) {
        appInfoMapper.updateAppInfo(appInfo);
        AppUpdateManage updateManage = new AppUpdateManage();
        updateManage.setAppId(appInfo.getId()); // 关联ID
        updateManage.setAppName(appInfo.getAppName());
        updateManage.setVersionCode(appInfo.getVersionCode());
        updateManage.setVersionName(appInfo.getVersionName());
        updateManage.setPlatform(appInfo.getPlatform());
        updateManageMapper.updateByAppId(updateManage);
    }

    @Transactional
    public void deleteAppInfo(Long id) {
        AppInfo appInfo = new AppInfo();
        appInfo.setId(id);
        appInfo.setIsDeleted("Y");          // 软删除标志
        appInfo.setGmtModified(new Date()); // 更新时间
        appInfoMapper.updateAppInfo(appInfo);
        updateManageMapper.updateIsDeletedByAppId(id); // 需实现此Mapper方法
    }

    public AppInfo getAppById(Long id) {
        // 实现根据ID查询逻辑，注意过滤已删除数据（isDeleted='N'）
        return appInfoMapper.selectById(id);
    }
}
