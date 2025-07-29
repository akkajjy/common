package com.palwy.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.entity.ClrDictDO;
import com.palwy.common.mapper.AppUpdateManageMapper;
import com.palwy.common.req.AppUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUpdateManageService {
    @Autowired
    private AppUpdateManageMapper mapper;
    @Resource
    private ClrDictService clrDictService;
    public PageInfo<AppUpdateManage> selectPage(AppUpdateReq manage, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AppUpdateManage> list = mapper.selectAppUpdateManageList(manage);
        List<ClrDictDO> appNameList = clrDictService.getClrDictListByDictType("AppNameEnum");
        Map<String, String> dictMap = appNameList.stream()
                .collect(Collectors.toMap(ClrDictDO::getDictValue, ClrDictDO::getDictLabel));

// 单层循环替换应用名称
        for (AppUpdateManage appInfo : list) {
            String dictLabel = dictMap.get(appInfo.getAppName());
            if (dictLabel != null) {
                appInfo.setAppName(dictLabel);
            }
        }
        PageInfo<AppUpdateManage> pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(list.size());
        return pageInfo;
    }

    public AppUpdateManage getById(Long id) {
        return mapper.selectAppUpdateManageById(id);
    }

    public AppUpdateManage getByVersionAndPlatform(String versionCode, String platform) {
        AppUpdateManage appUpdateManage = mapper.selectByVersionAndPlatform(versionCode, platform);
        if(appUpdateManage==null){
            return null;
        }
        if(appUpdateManage.getForceUpdateType().equals("2")){
            appUpdateManage = mapper.isMax(appUpdateManage.getAppName(), platform);
        }
        return appUpdateManage;
    }
    public void updateById(AppUpdateManage updateData){
        mapper.updateAppUpdateManage(updateData);
    }
}
