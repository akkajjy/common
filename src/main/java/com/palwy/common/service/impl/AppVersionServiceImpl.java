package com.palwy.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppInfoDO;
import com.palwy.common.entity.AppVersionDO;
import com.palwy.common.mapper.AppInfoDOMapper;
import com.palwy.common.mapper.AppVersionDOMapper;
import com.palwy.common.req.AppInfoReq;
import com.palwy.common.resp.AppVersionResp;
import com.palwy.common.service.AppVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionDOMapper appVersionDOMapper;
    @Autowired
    private AppInfoDOMapper appInfoDOMapper;
    @Override
    public List<AppVersionDO> getAllAppVersions() {
        return appVersionDOMapper.getAllAppVersions();
    }

    @Override
    public AppVersionResp getAppVersionById(Long id) {
        return appVersionDOMapper.getAppVersionById(id);
    }

    @Override
    public int saveAppVersion(AppInfoDO appInfoDO, AppInfoReq appInfoReq) {
        if (appInfoDO == null || appInfoReq == null) {
            log.debug("无效参数: appInfoDO={}, appInfoReq={}", appInfoDO, appInfoReq);
            return 0;
        }
        log.debug("开始插入APP版本信息, appId={}", appInfoDO.getId());

        List<AppVersionDO> addAppVersionDOList = new ArrayList<>();
        String channel = appInfoReq.getChannel();
        if (channel != null && !channel.isEmpty()) {
            AppVersionDO version = buildAppVersion(appInfoDO, appInfoReq, channel);
            addAppVersionDOList.add(version);
        }

        return batchInsertWithFallback(addAppVersionDOList);
    }

    // 抽取对象构建方法（避免重复代码）
    private AppVersionDO buildAppVersion(AppInfoDO appInfoDO, AppInfoReq req, String channel) {
        AppVersionDO entity = new AppVersionDO();
        entity.setAppId(appInfoDO.getId());
        entity.setVersionCode(req.getVersionCode());
        entity.setVersionName(req.getVersionName());
        entity.setDownloadUrl(req.getDownloadUrl());
        entity.setFilePath(req.getFilePath());
        entity.setChannel(channel);
        entity.setDeviceFilter(req.getDeviceFilter());
        entity.setShowCashLoan(req.getShowCashLoan());
        entity.setForceUpdateType(req.getForceUpdateType());
        entity.setUpdateDesc(req.getUpdateDesc());
        return entity;
    }

    // 7. 分批次插入 + 异常处理
    private int batchInsertWithFallback(List<AppVersionDO> dataList) {
        final int BATCH_SIZE = 500; // 每批500条[8](@ref)
        int totalCount = 0;

        for (int i = 0; i < dataList.size(); i += BATCH_SIZE) {
            List<AppVersionDO> batchList = dataList.subList(i, Math.min(i + BATCH_SIZE, dataList.size()));
            try {
                int count = appVersionDOMapper.saveBatchAppVersion(batchList);
                totalCount += count;
            } catch (DataAccessException e) {
                log.error("批次插入失败: 批次[{}-{}], 原因: {}",
                        i, i + batchList.size(), e.getMessage());
            }
        }
        return totalCount;
    }

    @Override
    public int updateAppVersion(AppVersionDO appVersion) {
        return appVersionDOMapper.updateAppVersion(appVersion);
    }

    @Override
    public int deleteAppVersion(Long id) {
        AppVersionDO appVersionDO =appVersionDOMapper.getAppInfo(id);
        AppInfoDO infoDO = appInfoDOMapper.getAppInfoById(appVersionDO.getAppId());
        appInfoDOMapper.deleteAppInfo(infoDO.getId());
        return appVersionDOMapper.deleteAppVersion(id);
    }

    @Override
    public int deleteVersionsByAppId(Long appId) {
        return appVersionDOMapper.deleteVersionsByAppId(appId);
    }

    @Override
    public int saveBatchAppVersion(List<AppVersionDO> versions) {
        return batchInsertWithFallback(versions);
    }

    public PageInfo<AppVersionResp> listAppVersionsByCondition(
            String appName,
            String osType,
            String channel,
            int pageNum,
            int pageSize) {

        // 启动分页
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询 - 传入osType参数
        List<AppVersionResp> list = appVersionDOMapper.listByCondition(
                appName, osType, channel);

        PageInfo<AppVersionResp> pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(list.size());
        return pageInfo;
    }

    @Override
    public boolean checkForceUpdate(String versionCode) {
        // 查询指定版本信息
        AppVersionDO version = appVersionDOMapper.getVersionByAppIdAndChannel(versionCode);

        // 判断强制更新状态
        return version != null && version.getForceUpdateType()>0;
    }
}
