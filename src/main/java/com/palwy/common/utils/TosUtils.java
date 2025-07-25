package com.palwy.common.utils;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.palwy.account.form.VolcengineUtilsRequest;
import com.palwy.account.utils.VolcengineUtils;
import com.palwy.common.constants.VolcengineConstants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TosUtils {

    @Resource
    private VolcengineConstants volcengineConstants;

    /**
     * 获取临时访问地址
     * @param realPath 桶路径
     * @return 临时地址
     */
    public String getTemporaryUrl(String realPath) {
        String temporaryUrl = StringUtils.EMPTY;
        if (StringUtils.isBlank(realPath)) {
            return temporaryUrl;
        }
        VolcengineUtilsRequest volcengineUtilsRequest = new VolcengineUtilsRequest();
        volcengineUtilsRequest.setRegion(volcengineConstants.getRegion());
        volcengineUtilsRequest.setEndpoint(volcengineConstants.getEndpoint());
        volcengineUtilsRequest.setAccessKey(volcengineConstants.getAccessKey());
        volcengineUtilsRequest.setSecretKey(volcengineConstants.getSecretKey());
        volcengineUtilsRequest.setBucketName(volcengineConstants.getBucketName());
        volcengineUtilsRequest.setTarget(volcengineConstants.getTarget());
        volcengineUtilsRequest.setRealPath(realPath);
        temporaryUrl = VolcengineUtils.getTemporaryUrl(volcengineUtilsRequest);
        return temporaryUrl;
    }
    }
