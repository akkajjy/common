package com.palwy.common.mapper;

import com.palwy.common.entity.AppInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.req.AppReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppInfoMapper {
    int insertAppInfo(AppInfo appInfo);
    int deleteAppInfo(@Param("id") Long id);
    int updateAppInfo(AppInfo appInfo);
    List<AppInfo> selectAppInfoByCondition(AppReq appReq);
    AppInfo selectById(@Param("id") Long id);
}
