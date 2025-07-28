package com.palwy.common.mapper;

import com.palwy.common.entity.AppUpdateManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppUpdateManageMapper {
    int insertAppUpdateManage(AppUpdateManage manage);
    int updateAppUpdateManage(AppUpdateManage manage);
    int deleteAppUpdateManageById(@Param("id") Long id);
    int updateIsDeletedByAppId(@Param("appId") Long appId);
    int updateByAppId(@Param("manage") AppUpdateManage manage);
    // 新增方法
    List<AppUpdateManage> selectAppUpdateManageList(AppUpdateManage manage);
    AppUpdateManage selectAppUpdateManageById(@Param("id")Long id);
    AppUpdateManage selectByVersionAndPlatform(
            @Param("versionCode") String versionCode,
            @Param("platform") String platform
    );
}
