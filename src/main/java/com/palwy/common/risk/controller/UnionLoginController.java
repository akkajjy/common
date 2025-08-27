package com.palwy.common.risk.controller;

import com.palwy.common.risk.domain.req.UnionLoginReq;
import com.palwy.common.risk.service.HyRiskService;
import com.palwy.common.risk.service.YjRiskService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "风控联合登录接口")
@RequestMapping("/v1/union")
@CrossOrigin(
        origins = {
                "https://installment.shhpalwy.com",
                "http://h5-installment-shop-test.shhpalwy.com",
                "http://localhost:8080",
                "http://zy-shop-test02.palwy.com"
        },
        allowCredentials = "true"
)
public class UnionLoginController {
    @Autowired
    private YjRiskService yjRiskService;

    @Autowired
    private HyRiskService hyRiskService;

    @ApiOperation(value = "生成联合登录URL",
            notes = "根据用户标识和手机号生成联合登录链接。手机号末位为奇数时调用华翊风控，偶数时调用优鉴风控")
    @GetMapping("/Login")
    public ResultVO<String> unionLogin(
            @ApiParam(value = "用户唯一标识", required = true, example = "user123")
            @RequestParam String uuid,
            @ApiParam(value = "用户手机号", required = true, example = "13800138000")
            @RequestParam String mobile) {

        log.info("联合登录请求参数 - Mobile: {}", mobile);

        try {
            // 检查手机号有效性
            if (mobile == null || mobile.trim().isEmpty()) {
                return ResultVOUtil.fail("手机号不能为空");
            }

            // 提取末位数字并判断奇偶性
            char lastChar = mobile.charAt(mobile.length() - 1);
            if (!Character.isDigit(lastChar)) {
                return ResultVOUtil.fail("手机号格式错误");
            }

            int lastDigit = Character.getNumericValue(lastChar);
            String redirectUrl;

            if (lastDigit % 2 == 1) { // 奇数：调用华翊风控
                log.info("手机号末位为奇数，启用华翊风控");
                UnionLoginReq req = new UnionLoginReq();
                req.setThirdUserId(uuid);
                //根据手机号查询商城用户信息进行组装
                redirectUrl = hyRiskService.generateUnionLoginUrl(req);
            } else { // 偶数：调用优鉴风控
                log.info("手机号末位为偶数，启用优鉴风控");
                redirectUrl = yjRiskService.generateUnionLoginUrl(uuid, mobile);
            }

            log.info("生成的联合登录URL: {}", redirectUrl);
            return ResultVOUtil.success(redirectUrl);

        } catch (RuntimeException e) {
            log.error("联合登录失败: {}", e.getMessage());
            return ResultVOUtil.fail("联合登录失败");
        } catch (Exception e) {
            log.error("服务器处理错误: {}", e.getMessage());
            return ResultVOUtil.fail("服务器内部错误");
        }
    }
}