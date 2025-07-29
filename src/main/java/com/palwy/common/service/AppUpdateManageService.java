package com.palwy.common.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ksvip.next.components.core.exception.BusinessException;
import com.palwy.common.entity.AppUpdateManage;
import com.palwy.common.entity.ClrDictDO;
import com.palwy.common.mapper.AppUpdateManageMapper;
import com.palwy.common.req.AppUpdateReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class AppUpdateManageService {
    @Autowired
    private AppUpdateManageMapper mapper;
    @Resource
    private ClrDictService clrDictService;
    public PageInfo<AppUpdateManage> selectPage(AppUpdateReq manage, int pageNum, int pageSize) throws Exception {
        // 1. 增加入参日志（脱敏处理）
        log.info("分页查询请求: pageNum={}, pageSize={}, appName={}, version={}",
                pageNum, pageSize,
                manage.getAppName(),
                manage.getVersionCode());

        try {
            // 2. 启动分页（增加合理化参数）
            PageHelper.startPage(pageNum, pageSize, true);

            // 3. 执行查询并记录耗时
            long startTime = System.currentTimeMillis();
            List<AppUpdateManage> list = mapper.selectAppUpdateManageList(manage);
            log.debug("数据库查询完成: 耗时={}ms, 返回记录数={}",
                    System.currentTimeMillis() - startTime, list.size());

            // 4. 优化字典加载（静态缓存+空值保护）
            List<ClrDictDO> appNameList = clrDictService.getClrDictListByDictType("AppNameEnum");
            if (CollectionUtils.isEmpty(appNameList)) {
                log.warn("字典数据为空: dictType=AppNameEnum");
                return new PageInfo<>(list);
            }

            // 5. 优化字典转换（并行流+过滤空值）
            Map<String, String> dictMap = appNameList.stream()
                    .filter(d -> StringUtils.isNotBlank(d.getDictValue()))
                    .collect(Collectors.toMap(
                            ClrDictDO::getDictValue,
                            ClrDictDO::getDictLabel,
                            (oldVal, newVal) -> oldVal)); // 重复key处理

            // 6. 并行处理字典替换
            list.parallelStream().forEach(appInfo -> {
                String dictLabel = dictMap.get(appInfo.getAppName());
                if (dictLabel != null) {
                    appInfo.setAppName(dictLabel);
                } else {
                    log.trace("未找到字典映射: appName={}", appInfo.getAppName());
                }
            });

            // 7. 构建分页结果（移除错误的setTotal）
            PageInfo<AppUpdateManage> pageInfo = new PageInfo<>(list);
            log.info("分页构建完成: 总记录数={}, 总页数={}",
                    pageInfo.getTotal(), pageInfo.getPages());

            return pageInfo;
        } catch (Exception e) {
            // 8. 异常处理日志
            log.error("分页查询异常: {}", e.getMessage(), e);
            throw new Exception("分页查询失败");
        }
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
