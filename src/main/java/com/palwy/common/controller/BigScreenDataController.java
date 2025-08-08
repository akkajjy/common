package com.palwy.common.controller;

import com.alibaba.fastjson.JSON;
import com.palwy.common.req.BigScreenDataVO;
import com.palwy.common.req.LoanSuperConfigVO;
import com.palwy.common.service.BigScreenDataService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping("/v1/big-screen-data")
@CrossOrigin(
        origins = {
                "https://datav-test.shhpalwy.com",
                "https://datav.shhpalwy.com/",
                "http://localhost:8080"
        },
        allowCredentials = "true"
)
@Api(tags = "大屏数据接口")
@Slf4j
public class BigScreenDataController {

    @Autowired
    private BigScreenDataService bigScreenDataService;

    @GetMapping("/getData")
    @ApiOperation("获取大屏数据")
    public ResultVO<BigScreenDataVO> getData() {
        log.info("---查询大屏数据开始---");
        BigScreenDataVO bigScreenDataVO = bigScreenDataService.getData();
        ResultVO returnVO = Objects.isNull(bigScreenDataVO) ? ResultVOUtil.fail("数据加载失败") : ResultVOUtil.success(bigScreenDataVO);
        log.info("查询大屏数据开始出参:{}", JSON.toJSONString(returnVO));
        return returnVO;
    }

}
