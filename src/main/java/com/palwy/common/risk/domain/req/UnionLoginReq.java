package com.palwy.common.risk.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("联合登录请求参数")
public class UnionLoginReq {

    @ApiModelProperty(value = "用户标识，联登标识", required = true)
    private String thirdUserId;

    @ApiModelProperty("用户身份证号")
    private String userCardId;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户手机号")
    private String userPhone;

    @ApiModelProperty("商户重定向地址，用户查看报告后，通过报告页按钮返回到商户指定的界面。仅限公众号模式")
    private String returnUrl;

    @ApiModelProperty("映射资源位标识")
    private String mappingType;

    @ApiModelProperty("精准运营资源位标识")
    private String mappingEntrance;

    @ApiModelProperty("用户自定义字段")
    private String userDefine;

    @ApiModelProperty("0：非会员；1：会员")
    private String isVip;

    @ApiModelProperty("客户自定义天创完成某个操作之后回退到客户指定页面")
    private String cusDefineReturnUrl;

    @ApiModelProperty("会员号编码")
    private String cusMemNo;

    @ApiModelProperty("组件支付失败场景下联登，固定传值1")
    private String source;

}