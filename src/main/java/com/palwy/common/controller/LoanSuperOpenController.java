package com.palwy.common.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.palwy.common.req.LoanSuperClickVO;
import com.palwy.common.req.LoanSuperConfigVO;
import com.palwy.common.service.LoanSuperService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/loan/super/open")
@CrossOrigin(
        origins = {
                "https://installment.shhpalwy.com",
                "https://h5-installment-shop-test.shhpalwy.com",
                "http://localhost:8080",
                "https://zy-shop-test02.palwy.com",
                "http://zy-shop-test02.palwy.com",
                "http://zy-shop-test.shhpalwy.com",
                "http://zy-shop-test03.palwy.com"
        },
        allowCredentials = "true"
)
@Api(tags = "贷超接口")
@Slf4j
public class LoanSuperOpenController {

    @Autowired
    private LoanSuperService loanSuperService;

    @PostMapping("/getList")
    @ApiOperation("查询配置列表")
    public ResultVO<List<LoanSuperConfigVO>> getList() {
        log.info("---贷超查询配置列表开始---");
        List<LoanSuperConfigVO> list = loanSuperService.getList();
        ResultVO returnVO = ResultVOUtil.success(list);
        log.info("贷超查询配置列表出参:{}", JSON.toJSONString(returnVO));
        return returnVO;
    }

    @PostMapping("/click")
    @ApiOperation("点击链接")
    public ResultVO click(@RequestBody @Valid LoanSuperClickVO loanSuperClickVO) {
        log.info("贷超点击链接入参:{}", JSON.toJSONString(loanSuperClickVO));
        ResultVO returnVO = loanSuperService.click(loanSuperClickVO);
        log.info("贷超点击链接出参:{}", JSON.toJSONString(returnVO));
        return returnVO;
    }

}
