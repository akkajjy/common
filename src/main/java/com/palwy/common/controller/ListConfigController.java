package com.palwy.common.controller;


import com.palwy.common.entity.ListConfig;
import com.palwy.common.entity.ListConfigReq;
import com.palwy.common.service.ListConfigService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/listConfig")
public class ListConfigController {

    @Resource
    private ListConfigService listConfigService;


    @PostMapping("/show")
    public ResultVO getShowConfigs(@RequestBody ListConfigReq req) {
        ListConfig  listConfig1 =listConfigService.selectByCondition(req);

        return ResultVOUtil.success("查询成功",listConfig1);
    }
}