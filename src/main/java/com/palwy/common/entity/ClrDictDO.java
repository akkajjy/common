package com.palwy.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: lihaonan
 * @create: 2025/6/17 13:34
 * 描述：字典数据表
 **/
@Data
public class ClrDictDO {

    @ApiModelProperty("编号")
    private Long id;

    @ApiModelProperty("字典类型（业务标识，如：order_status、user_gender）")
    private String dictType;

    @ApiModelProperty("字典值（下拉框value，如：\"1\"、\"pending\"）")
    private String dictValue;

    @ApiModelProperty("字典标签（下拉框显示文本，如：\"待支付\"、\"男\"）")
    private String dictLabel;

    @ApiModelProperty("排序（数值越小越靠前）")
    private int sort;

    @ApiModelProperty("状态（1-启用，0-禁用）")
    private int status;

    @ApiModelProperty("备注（描述字典含义或业务规则）")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("修改时间")
    private Date gmtModified;
}
