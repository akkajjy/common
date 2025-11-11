package com.palwy.common.banner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "首页背景图响应信息")
public class HomeBannerResponse {

    @ApiModelProperty(value = "主键ID", example = "1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "主题名称", example = "春节")
    private String theme;

    @ApiModelProperty(value = "小图URL", example = "/images/small.jpg")
    private String smallImage;

    @ApiModelProperty(value = "大图URL", example = "/images/large.jpg")
    private String largeImage;

    @ApiModelProperty(value = "小图URL（适配除立即申请/重新申请外场景）", required = true, example = "/images/small.jpg")
    private String smallImageFile;

    @ApiModelProperty(value = "大图URL（适配立即申请/重新申请场景）", required = true, example = "/images/large.jpg")
    private String largeImageFile;

    @ApiModelProperty(value = "状态：0-下架，1-上架", example = "1")
    private Integer status;

    @ApiModelProperty(value = "创建时间", example = "2025-08-20 20:23:23")
    private Date createTime;

    @ApiModelProperty(value = "状态描述", example = "上架")
    private String statusDesc;
}
