package com.palwy.common.mapper;

import com.palwy.common.entity.AppVersionDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppVersionDOMapper {

    int saveAppVersion(AppVersionDO appVersion);

    int saveBatchAppVersion(@Param("list")List<AppVersionDO> appVersionDOList);

    int updateAppVersion(AppVersionDO appVersion);

    int deleteAppVersion(@Param("id") Long id, @Param("modifier") String modifier);

    AppVersionDO getAppVersionById(@Param("id") Long id);

    List<AppVersionDO> getAllAppVersions();

    int deleteVersionsByAppId(Long appId);
}
