package com.palwy.common.controller;


import com.palwy.common.entity.ListConfig;
import com.palwy.common.entity.ListConfigReq;
import com.palwy.common.service.ListConfigService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/listConfig")
@Slf4j
public class ListConfigController {

    @Resource
    private ListConfigService listConfigService;


    @PostMapping("/show")
    public ResultVO getShowConfigs(@RequestBody ListConfigReq req) {

        return  listConfigService.selectByCondition(req);
    }
}