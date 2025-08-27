package com.palwy.common.banner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "首页背景图添加/编辑请求")
public class HomeBannerRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "主键ID", example = "1",required = true)
    private Long id;

    @NotBlank(message = "主题不能为空")
    @ApiModelProperty(value = "主题名称", required = true, example = "春节")
    private String theme;

    @NotBlank(message = "小图不能为空")
    @ApiModelProperty(value = "小图URL", required = true, example = "/images/small.jpg")
    private String smallImage;

    @NotBlank(message = "大图不能为空")
    @ApiModelProperty(value = "大图URL", required = true, example = "/images/large.jpg")
    private String largeImage;

    @NotBlank(message = "小图文件路径不能为空")
    @ApiModelProperty(value = "小图文件路径", required = true, example = "/images/small.jpg")
    private String smallImageFile;

    @NotBlank(message = "大图文件路径不能为空")
    @ApiModelProperty(value = "大图文件路径", required = true, example = "/images/large.jpg")
    private String largeImageFile;
}
