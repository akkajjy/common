package com.palwy.common.risk.utils.hy.utils;

/**
 * @description: 枚举集合
 * @author: juyang.liang
 * @since: 2023-03-23 10:54
 * @updatedUser:
 * @updatedDate:
 * @updatedRemark:
 * @version:
 */
public class Enums {
    public enum RequestAddress {
        INDEX("index", "标准签名URL首页跳转"),
        PAY_INDEX("payIndex", "商户 app跳转H5支付"),
        UNION_LOGIN_INDEX("unionLoginIndex", "商户联登"),
        GET_MERCHANT_UNION_URL("getMerchantUnionUrl", "获取联登跳转地址"),
        ;
        private String value;
        private String msg;

        RequestAddress(String value, String msg) {
            this.value = value;
            this.msg = msg;
        }

        public String getValue() {
            return this.value;
        }

        public String getMsg() {
            return this.msg;
        }
    }


//    支付类型先支付模式（1、微信,2、支付宝）

    public enum PayType {
        WX_PAY("1", "微信"),
        ALI_PAY("2", "支付宝"),
        ;
        private String value;
        private String msg;

        PayType(String value, String msg) {
            this.value = value;
            this.msg = msg;
        }

        public String getValue() {
            return this.value;
        }

        public String getMsg() {
            return this.msg;
        }
    }

}
