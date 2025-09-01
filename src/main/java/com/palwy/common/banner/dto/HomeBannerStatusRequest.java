package com.palwy.common.banner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel(description = "首页背景图状态更新请求")
public class HomeBannerStatusRequest {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "背景图ID", required = true, example = "1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态：0-下架，1-上架", required = true, example = "1")
    private Integer status;
}
