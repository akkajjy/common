package com.palwy.common.controller;

import com.github.pagehelper.PageInfo;
import com.palwy.common.entity.AppVersionDO;
import com.palwy.common.req.AppVersionQueryReq;
import com.palwy.common.resp.AppVersionResp;
import com.palwy.common.service.AppVersionService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app-version")
public class AppVersionController {
    @Autowired
    private AppVersionService appVersionService;

    @PostMapping("/query")
    public ResultVO<PageInfo<AppVersionResp>> listAppVersions(@RequestBody AppVersionQueryReq queryReq) {

        // 验证分页参数
        if (queryReq.getPageNum() == null || queryReq.getPageNum() < 1) {
            queryReq.setPageNum(1);
        }
        if (queryReq.getPageSize() == null || queryReq.getPageSize() < 1) {
            queryReq.setPageSize(10);
        } else if (queryReq.getPageSize() > 100) {
            queryReq.setPageSize(100); // 最大100条/页
        }

        PageInfo<AppVersionResp> pageInfo = appVersionService.listAppVersionsByCondition(
                queryReq.getAppName(),
                queryReq.getOsType(),
                queryReq.getChannels(),
                queryReq.getPageNum(),
                queryReq.getPageSize());

        // 自定义响应结构
        return ResultVOUtil.success(pageInfo);
    }
}
