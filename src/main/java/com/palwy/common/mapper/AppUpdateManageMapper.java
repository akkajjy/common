package com.palwy.common.mapper;

import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.req.AppUpdateReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppUpdateManageMapper {
    int insertAppUpdateManage(AppUpdateManage manage);
    int updateAppUpdateManage(AppUpdateManage manage);
    int deleteAppUpdateManageById(@Param("id") Long id);
    int updateIsDeletedByAppId(@Param("appId") Long appId);
    int updateByAppId(@Param("updateManage") AppUpdateManage manage);
    // 新增方法
    List<AppUpdateManage> selectAppUpdateManageList(AppUpdateReq manage);
    AppUpdateManage selectAppUpdateManageById(@Param("id")Long id);
    AppUpdateManage selectByVersionAndPlatform(@Param("appName") String appName,
            @Param("versionCode") String versionCode,
            @Param("platform") String platform
    );
    AppUpdateManage isMax(@Param("appName") String appName,
                           @Param("platform") String platform);

    AppUpdateManage isAdd(@Param("appName") String appName,
                          @Param("platform") String platform,
                          @Param("versionCode") String versionCode);
}
