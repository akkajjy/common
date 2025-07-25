package com.palwy.common.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data
public class AppUpdateManageReq {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("应用名称")
    @NotBlank(message = "应用名称不能为空")
    private String appName;

    @ApiModelProperty("版本号")
    @NotBlank(message = "版本号不能为空")
    private String versionCode;

    @ApiModelProperty("版本名称")
    @NotBlank(message = "版本名称不能为空")
    private String versionName;

    @ApiModelProperty("平台")
    @NotBlank(message = "发版平台不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<String> platform;

    @ApiModelProperty("强制更新类型(0:不更新 1:不强制更新 2:强制更新)")
    @NotNull(message = "强制更新类型不能为空")
    @Pattern(regexp = "^[012]$", message = "强制更新类型值不合法，只能是0、1或2")
    private String forceUpdateType;

    @ApiModelProperty("最低支持版本号")
    @NotBlank(message = "最低支持版本号不能为空")
    private String lowVersionCode;

    @ApiModelProperty("更新描述")
    private String updateDesc;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("修改人")
    private String modifier;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    @ApiModelProperty("删除标识(N:未删除 Y:已删除)")
    @Pattern(regexp = "^[NY]$", message = "删除标识值不合法，只能是N或Y")
    private String isDeleted = "N"; // 默认值

    @ApiModelProperty("系统类型(1:Android 2:iOS)")
    @NotNull(message = "系统类型不能为空")
    @Pattern(regexp = "^[12]$", message = "系统类型值不合法，只能是1或2")
    private String osType;
}
