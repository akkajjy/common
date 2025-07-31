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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/loan/super")
@Api(tags = "贷超接口")
@Slf4j
public class LoanSuperController {

    @Autowired
    private LoanSuperService loanSuperService;

    @PostMapping("/pageList")
    @ApiOperation("分页查询配置列表")
    public ResultVO<PageInfo<LoanSuperConfigVO>> pageList(@RequestBody LoanSuperConfigVO req) {
        log.info("贷超分页查询配置列表入参:{}", JSON.toJSONString(req));
        PageInfo<LoanSuperConfigVO> pageInfo = loanSuperService.getPageList(req);
        log.info("贷超分页查询配置列表出参:{}", JSON.toJSONString(pageInfo));
        return ResultVOUtil.success(pageInfo);
    }

    @PostMapping("/create")
    @ApiOperation("新增配置")
    public ResultVO createConfig(@RequestBody LoanSuperConfigVO req) {
        log.info("贷超新增配置入参:{}", JSON.toJSONString(req));
        ResultVO returnVO = loanSuperService.createConfig(req);
        log.info("贷超新增配置出参:{}", JSON.toJSONString(returnVO));
        return returnVO;
    }

    @PostMapping("/update")
    @ApiOperation("修改配置")
    public ResultVO updateConfig(@RequestBody LoanSuperConfigVO req) {
        log.info("贷超修改配置入参:{}", JSON.toJSONString(req));
        ResultVO returnVO = loanSuperService.updateConfig(req);
        log.info("贷超修改配置出参:{}", JSON.toJSONString(returnVO));
        return returnVO;
    }

    @PostMapping("/getList")
    @ApiOperation("查询配置列表")
    public ResultVO<List<LoanSuperConfigVO>> getList() {
        log.info("---贷超查询配置列表开始---");
        List<LoanSuperConfigVO> list = loanSuperService.getList();
        ResultVO returnVO = ResultVOUtil.success(list);
        log.info("贷超修改配置出参:{}", JSON.toJSONString(returnVO));
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
