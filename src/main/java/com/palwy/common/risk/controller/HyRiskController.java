package com.palwy.common.risk.controller;

import com.alibaba.fastjson.JSON;
import com.palwy.common.risk.domain.req.UnionLoginReq;
import com.palwy.common.risk.service.HyRiskService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/vi/risk")
@Api(tags = "华翊风控联合登录接口") // 添加类级描述
public class HyRiskController {

    private HyRiskService hyRiskService;

    @ApiOperation(value = "加密联合登录数据",
            notes = "对联合登录请求参数进行加密和签名处理")
    @PostMapping("/unionLogin/encrypt")
    public ResultVO<String> encryptAndSignUnionLogin(
            @ApiParam(value = "联合登录请求参数", required = true)
            @RequestBody UnionLoginReq req) {

        log.info("联登请求参数:{}", JSON.toJSONString(req));
        try {
            Map<String, String> result = hyRiskService.encryptAndSignUnionLogin(req);
            return ResultVOUtil.success(result);
        } catch (RuntimeException e) {
            log.info("联登失败:{}",e.getMessage());
            return ResultVOUtil.fail("联登失败");
        } catch (Exception e) {
            log.info("联登失败,服务器处理错误:{}",e.getMessage());
            return ResultVOUtil.fail("服务器处理错误");
        }
    }
}