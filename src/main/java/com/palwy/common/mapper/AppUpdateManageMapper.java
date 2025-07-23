package com.palwy.common.mapper;

import com.palwy.common.entity.AppUpdateManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppUpdateManageMapper {
    int insert(AppUpdateManage record);
    int update(AppUpdateManage record);
    int delete(Integer id);
    AppUpdateManage selectById(Integer id);
    List<AppUpdateManage> selectAll();

    // 分页查询
    List<AppUpdateManage> selectByPage(@Param("appName") String appName,
                                       @Param("osType") String osType);

    // 强制更新查询接口
    AppUpdateManage selectForceUpdate(@Param("osType") String osType,
                                      @Param("versionCode") String versionCode,
                                      @Param("platform") String platform);
}
