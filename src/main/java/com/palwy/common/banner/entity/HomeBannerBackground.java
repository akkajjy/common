package com.palwy.common.banner.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ApiModel(description = "首页背景图配置实体")
public class HomeBannerBackground {

    @ApiModelProperty(value = "主键ID", example = "1", hidden = true)
    private Long id;

    @NotBlank(message = "主题不能为空")
    @ApiModelProperty(value = "主题名称", required = true, example = "春节")
    private String theme;

    @NotBlank(message = "小图不能为空")
    @ApiModelProperty(value = "小图URL（适配除立即申请/重新申请外场景）", required = true, example = "/images/small.jpg")
    private String smallImage;

    @NotBlank(message = "大图不能为空")
    @ApiModelProperty(value = "大图URL（适配立即申请/重新申请场景）", required = true, example = "/images/large.jpg")
    private String largeImage;

    @NotBlank(message = "小图路径不能为空")
    @ApiModelProperty(value = "小图URL（适配除立即申请/重新申请外场景）", required = true, example = "/images/small.jpg")
    private String smallImageFile;

    @NotBlank(message = "路径不能为空")
    @ApiModelProperty(value = "大图URL（适配立即申请/重新申请场景）", required = true, example = "/images/large.jpg")
    private String largeImageFile;

    @ApiModelProperty(value = "状态：0-下架，1-上架", example = "1")
    private Integer status;

    @ApiModelProperty(value = "创建时间", example = "2025-08-20 20:23:23", hidden = true)
    private Date gmtCreated;

    @ApiModelProperty(value = "更新时间", example = "2025-08-21 10:15:30", hidden = true)
    private Date gmtModified;

    @ApiModelProperty(value = "删除标识：N-未删除；Y-已删除", example = "N", hidden = true)
    private String isDeleted;

    @ApiModelProperty(value = "创建人", example = "system", hidden = true)
    private String creator;

    @ApiModelProperty(value = "更新人", example = "system", hidden = true)
    private String modifier;

    // 构造函数
    public HomeBannerBackground() {
        this.status = 0; // 默认下架状态
        this.isDeleted = "N"; // 默认未删除
        this.creator = "system";
        this.modifier = "system";
    }
}
