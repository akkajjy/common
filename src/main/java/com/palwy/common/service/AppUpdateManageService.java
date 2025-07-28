package com.palwy.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.mapper.AppUpdateManageMapper;
import com.palwy.common.req.AppUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUpdateManageService {
    @Autowired
    private AppUpdateManageMapper mapper;
    public PageInfo<AppUpdateManage> selectPage(AppUpdateReq manage, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AppUpdateManage> list = mapper.selectAppUpdateManageList(manage);
        PageInfo<AppUpdateManage> pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(list.size());
        return pageInfo;
    }

    public AppUpdateManage getById(Long id) {
        return mapper.selectAppUpdateManageById(id);
    }

    public AppUpdateManage getByVersionAndPlatform(String versionCode, String platform) {
        return mapper.selectByVersionAndPlatform(versionCode, platform);
    }
    public void updateById(AppUpdateManage updateData){
        mapper.updateByAppId(updateData);
    }
}
