package com.palwy.common.service;

import com.palwy.common.entity.AppVersionDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppVersionMapper {

    void saveAppVersion(AppVersionDO appVersion);

    void updateAppVersion(AppVersionDO appVersion);

    void deleteAppVersion(@Param("id") Long id, @Param("modifier") String modifier);

    AppVersionDO getAppVersionById(@Param("id") Long id);

    List<AppVersionDO> getAllAppVersions();

    // 新增方法：获取指定应用的所有版本
    List<AppVersionDO> getAllAppVersionsByAppId(@Param("appId") Integer appId);
}
