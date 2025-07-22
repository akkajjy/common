package com.palwy.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.palwy.common.entity.AppInfoDO;
import com.palwy.common.entity.AppVersionDO;
import com.palwy.common.mapper.AppVersionDOMapper;
import com.palwy.common.req.AppInfoReq;
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

    @Override
    public List<AppVersionDO> getAllAppVersions() {
        return appVersionDOMapper.getAllAppVersions();
    }

    @Override
    public AppVersionDO getAppVersionById(Long id) {
        return appVersionDOMapper.getAppVersionById(id);
    }

    @Override
    public int saveAppVersion(AppInfoDO appInfoDO, AppInfoReq appInfoReq) {
        // 参数校验优化：精准校验必要参数
        if (appInfoDO == null || appInfoReq == null) {
            log.debug("无效参数: appInfoDO={}, appInfoReq={}", appInfoDO, appInfoReq);
            return 0;
        }

        // 日志级别降级 & 精简日志内容
        log.debug("开始插入APP版本信息, appId={}", appInfoDO.getId());

        // 使用Stream API优化集合操作
        List<AppVersionDO> addAppVersionDOList = appInfoReq.getChannelList().stream()
                .filter(channel -> !channel.isEmpty()) // 过滤空渠道[2](@ref)
                .map(channel -> buildAppVersion(appInfoDO, appInfoReq, channel)) // 抽取构建方法
                .collect(Collectors.toList());

        // 分批次插入（防止超大数据量）
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
    public int deleteAppVersion(Long id, String modifier) {
        return appVersionDOMapper.deleteAppVersion(id, modifier);
    }
}
