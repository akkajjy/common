package com.palwy.common.risk.domain.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginResp {
    @ApiModelProperty(value = "url", required = true)
    private String loginUrl;
}
