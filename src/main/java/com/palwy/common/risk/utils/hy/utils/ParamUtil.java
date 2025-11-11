package com.palwy.common.risk.utils.hy.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * 参数整理工具
 *
 * @author zl.t
 * @date 2017年5月17日
 */
public class ParamUtil {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ParamUtil.class);

    /**
     * 格式化参数，按key按字典序排列并以传入的分隔符隔开，生成形如key1=value1,key2=value2...的字符串
     *
     * @param params 简单参数键值对
     * @param sep    分隔符
     * @return key=value${sep}key=value...格式的字符串
     */
    public static String sortMap(Map<String, String> params, String sep) {
        // 参数map为空时返回空串
        if (null == params || params.size() == 0) {
            return "";
        }
        // 按key排序
        StringBuilder sb = new StringBuilder();
        Map<String, String> sortMap = new TreeMap<String, String>();
        sortMap.putAll(params);
        for (Map.Entry<String, String> entry : sortMap.entrySet()) {
            if (StringUtils.isBlank(entry.getValue())) {
                continue;
            }
            sb.append(sep).append(entry.getKey()).append("=")
                    .append(entry.getValue());
        }
        // 返回结果字符串
        if (sb.length() > 1) {
            return sb.substring(1);
        }
        return "";
    }
}
