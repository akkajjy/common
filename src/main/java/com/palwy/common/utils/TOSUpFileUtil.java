package com.palwy.common.utils;

import com.volcengine.tos.model.object.*;
import com.volcengine.tos.comm.HttpMethod;
import com.volcengine.tos.TosClientException;
import com.volcengine.tos.TosServerException;
import com.volcengine.tos.TOSV2;
import com.volcengine.tos.TOSV2ClientBuilder;
import com.volcengine.tos.comm.common.ACLType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class TOSUpFileUtil {
    @Value("${tos.cn}")
    private String ENDPOINT;
    @Value("${tos.sh}")
    private String REGION;
    @Value("${tos.key}")
    private String ACCESS_KEY;
    @Value("${tos.secret}")
    private String ENCODED_SECRET;
    @Value("${tos.bucket}")
    private String BUCKET;
    @Value("${tos.url}")
    private String FINAL_URL;
    @Value("${spring.profiles.active}")
    private String ENV;

    // 客户端单例
    private volatile TOSV2 tosClient;

    public enum BusinessType {
        FUND_PARTY, LIGHT_ASSET, MEMBER_BENEFITS, SELF_OPERATED
    }

    public String uploadFile(BusinessType businessType, String userId,
                                    String filename, InputStream fileStream) {
        String objectKey = generateObjectKey(businessType, userId, filename);
        try {
            // 1. 上传文件
            PutObjectInput putRequest = new PutObjectInput()
                    .setBucket(BUCKET)
                    .setKey(objectKey)
                    .setContent(fileStream);

            PutObjectOutput output = getTosClient().putObject(putRequest);
            if (output == null || output.getEtag() == null) {
                log.error("文件上传失败");
                return null;
            }

            // 2. 设置 ACL
            setObjectPublicRead(objectKey);
            return generateFileUrl(objectKey);
        } catch (TosClientException e) {
            log.error("客户端参数错误: {}", e.getMessage());
        } catch (TosServerException e) {
            log.error("服务端响应异常 [{}] {}", e.getStatusCode(), e.getCode());
        } catch (Exception e) {
            log.error("系统异常: {}", e.getMessage());
        }
        return null;
    }

    private void setObjectPublicRead(String objectKey) {
        try {
            log.debug("设置 ACL，objectKey: {}", objectKey);
            PutObjectACLInput aclInput = new PutObjectACLInput()
                    .setBucket(BUCKET)
                    .setKey(objectKey)
                    .setAcl(ACLType.ACL_PUBLIC_READ);

            getTosClient().putObjectAcl(aclInput);
        } catch (TosServerException e) {
            log.error("ACL 设置失败: [{}] {}", e.getStatusCode(), e.getCode());
        }
    }

    /**
     * 构建对象存储路径
     */
    private String generateObjectKey(BusinessType businessType, String userId, String filename) {
        String dateDir = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return String.format("contract/%s/%s/%s/%s_%s",  // 增加环境层级
                ENV,                // 环境变量（dev/test/prod）
                businessType.name(),
                dateDir,
                userId,
                filename.replaceAll("[^a-zA-Z0-9-_.]", "_"));
    }

    /**
     * 构建文件访问URL
     */
    private String generateFileUrl(String objectKey) {
        return String.format("http://%s/%s", FINAL_URL, objectKey);
    }

    /**
     * 初始化TOS客户端（双重校验锁单例）
     */
    private TOSV2 getTosClient() {
        if (tosClient == null) {
            synchronized (TOSUpFileUtil.class) {
                if (tosClient == null) {
                    tosClient = new TOSV2ClientBuilder()
                            .build(REGION, ENDPOINT, ACCESS_KEY, ENCODED_SECRET);
                }
            }
        }
        return tosClient;
    }


    /**
     * 生成私有文件的临时访问URL (预签名URL)
     * @param objectKey 存储对象的完整路径 (如: installationPackage/ios/TDDFQ_1.0.2.ipa)
     * @param expiryDurationMinutes 链接有效期 (单位: 分钟)
     * @return 临时访问URL (过期失效)
     */
    public String generatePresignedUrl(String objectKey, int expiryDurationMinutes) {
        try {
            // 使用自定义域名创建专用客户端
            TOSV2 client = createCustomDomainClient();

            long expiresSeconds = Duration.ofMinutes(expiryDurationMinutes).getSeconds();

            PreSignedURLInput input = new PreSignedURLInput()
                    .setHttpMethod(HttpMethod.GET)
                    .setBucket(BUCKET)
                    .setKey(objectKey)
                    .setExpires(expiresSeconds);

            PreSignedURLOutput output = client.preSignedURL(input);
            return output.getSignedUrl();

        } catch (TosClientException e) {
            log.error("客户端错误: {}", e.getMessage());
        } catch (TosServerException e) {
            log.error("服务端错误: 状态码={}, 错误码={}", e.getStatusCode(), e.getCode());
        } catch (Exception e) {
            log.error("未知错误: {}", e.getMessage());
        }
        return null;
    }

    private TOSV2 createCustomDomainClient() {
        return new TOSV2ClientBuilder()
                .build(REGION, FINAL_URL, ACCESS_KEY, ENCODED_SECRET);
    }
}
