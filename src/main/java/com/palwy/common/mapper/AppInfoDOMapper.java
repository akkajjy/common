package com.palwy.common.mapper;

import com.palwy.common.entity.AppInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppInfoDOMapper {

    List<AppInfoDO> getAllAppInfos();

    AppInfoDO getAppInfoById(Long id);

    int saveAppInfo(AppInfoDO appInfo);

    AppInfoDO findByAppNameAndOsType(@Param("appName") String appName, @Param("osType") String osType);

    int updateAppInfo(AppInfoDO appInfo);

    Integer getAppInfoByNameAndVersion(String appName,String versionCode);
}
