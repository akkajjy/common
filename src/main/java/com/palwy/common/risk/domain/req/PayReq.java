package com.palwy.common.risk.domain.req;

import lombok.Data;

@Data
public class PayReq {
    // 商户资源位appId（由风险洞察平台提供）
    private String appId;

    // 用户标识（商户用户唯一标识）
    private String thirdUserId;

    // 支付类型(1:微信；2:支付宝;3:协议支付)
    private String payType;

    // 产品价格（单位：元，可选）
    private String productPrice;
}