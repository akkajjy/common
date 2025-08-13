package com.palwy.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoanSuperClickVO {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("身份证号")
    private String certNo;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("产品编号")
    @NotBlank(message = "产品编号不可为空")
    private String prdCode;
}
