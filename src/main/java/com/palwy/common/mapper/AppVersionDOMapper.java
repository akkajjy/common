package com.palwy.common.mapper;

import com.palwy.common.entity.AppVersionDO;
import com.palwy.common.resp.AppVersionResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppVersionDOMapper {

    int saveAppVersion(AppVersionDO appVersion);

    int saveBatchAppVersion(@Param("list")List<AppVersionDO> appVersionDOList);

    int updateAppVersion(AppVersionDO appVersion);

    int deleteAppVersion(@Param("id") Long id);

    AppVersionResp getAppVersionById(@Param("id") Long id);

    List<AppVersionDO> getAllAppVersions();

    int deleteVersionsByAppId(Long appId);


    List<AppVersionResp> listByCondition(@Param("appName") String appName,
                                         @Param("osType") String osType,
                                         @Param("channels") List<String> channels);

    AppVersionDO getVersionByAppIdAndChannel(@Param("versionCode") String versionCode);
}
