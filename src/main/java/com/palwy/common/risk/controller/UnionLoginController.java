package com.palwy.common.risk.controller;

import com.alibaba.fastjson.JSON;
import com.palwy.common.risk.domain.req.UnionLoginReq;
import com.palwy.common.risk.domain.resp.LoginResp;
import com.palwy.common.risk.service.HyRiskService;
import com.palwy.common.risk.service.YjRiskService;
import com.palwy.common.risk.utils.WeightCategoryUtils;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "风控联合登录接口")
@RequestMapping("/v1/union")
@CrossOrigin(
        origins = {
                "https://installment.shhpalwy.com",
                "https://h5-installment-shop-test.shhpalwy.com",
                "http://localhost:8080",
                "https://zy-shop-test02.palwy.com"
        },
        allowCredentials = "true"
)
public class UnionLoginController {

    @Value("${risk.report.category}")
    private String category;

    @Value("${risk.report.weight}")
    private String weight;

    @Autowired
    private YjRiskService yjRiskService;

    @Autowired
    private HyRiskService hyRiskService;

    @Autowired
    private WeightCategoryUtils weightCategoryUtils;

    @ApiOperation(value = "生成联合登录URL",
            notes = "根据用户标识和手机号生成联合登录链接。手机号末位为奇数时调用华翊风控，偶数时调用优鉴风控")
    @PostMapping("/Login")
    public ResultVO<LoginResp> unionLogin(@RequestBody UnionLoginReq unionLoginReq) {

        log.info("联合登录请求参数 : {}", JSON.toJSONString(unionLoginReq));

        try {
            // 检查手机号有效性
            if (unionLoginReq.getUserPhone() == null || unionLoginReq.getUserPhone().trim().isEmpty()) {
                return ResultVOUtil.fail("手机号不能为空");
            }
            String redirectUrl;
            String type = weightCategoryUtils.getWeightRandomRightsType(unionLoginReq.getUserPhone(), category,weight);
            if (StringUtils.equals(type,"hy")) { // 奇数：调用华翊风控
                log.info("手机号末位为奇数，启用华翊风控");
                //根据手机号查询商城用户信息进行组装
                redirectUrl = hyRiskService.generateUnionLoginUrl(unionLoginReq);
            } else { // 偶数：调用优鉴风控
                log.info("手机号末位为偶数，启用优鉴风控");
                redirectUrl = yjRiskService.generateUnionLoginUrl(unionLoginReq.getThirdUserId(), unionLoginReq.getUserPhone());
            }

            log.info("生成的联合登录URL: {}", redirectUrl);
            LoginResp loginResp = new LoginResp();
            loginResp.setLoginUrl(redirectUrl);
            return ResultVOUtil.success(loginResp);

        } catch (RuntimeException e) {
            log.error("联合登录失败: {}", e.getMessage());
            return ResultVOUtil.fail("联合登录失败");
        } catch (Exception e) {
            log.error("服务器处理错误: {}", e.getMessage());
            return ResultVOUtil.fail("服务器内部错误");
        }
    }
}