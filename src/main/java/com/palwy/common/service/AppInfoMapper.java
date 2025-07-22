package com.palwy.common.service;

import com.palwy.common.entity.AppInfoDO;
import com.palwy.common.entity.AppVersionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppInfoMapper {

    List<AppInfoDO> getAllAppInfos();

    AppInfoDO getAppInfoById(Long id);

    void saveAppInfo(AppInfoDO appInfo);

    AppInfoDO findByAppNameAndOsType(@Param("appName") String appName, @Param("osType") String osType);

    // 新增方法：获取指定应用的所有版本
    List<AppVersionDO> getAllAppVersionsByAppId(@Param("appId") Integer appId);
}
