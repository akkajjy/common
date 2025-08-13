package com.palwy.common.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@Data
@Component
@RefreshScope
public class VolcengineConstants {

    @Value("${tos.sh}")
    private String region;
    @Value("${tos.cn}")
    private String endpoint;
    @Value("${tos.key}")
    private String accessKey;
    @Value("${tos.secret}")
    private String secretKey;
    @Value("${tos.bucket}")
    private String bucketName;
    private String target="TDDCredit";


}
