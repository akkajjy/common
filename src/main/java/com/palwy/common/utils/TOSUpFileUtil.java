package com.palwy.common.utils;

import com.palwy.common.req.TOSRespVO;
import com.volcengine.tos.*;
import com.volcengine.tos.auth.StaticCredentials;
import com.volcengine.tos.model.object.*;
import com.volcengine.tos.comm.HttpMethod;
import com.volcengine.tos.comm.common.ACLType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TOSUpFileUtil {
    // 配置常量（建议抽离到配置中心）
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

    public TOSRespVO uploadFile(BusinessType businessType, String filename, InputStream fileStream) {
        String objectKey = generateObjectKey(businessType, filename);
        return this.upload(objectKey,fileStream);
    }

    public TOSRespVO upload(String objectKey, InputStream fileStream){
        try {
            // 1. 上传文件
            PutObjectInput putRequest = new PutObjectInput()
                    .setBucket(BUCKET)
                    .setKey(objectKey)
                    .setContent(fileStream);
            PutObjectOutput output = getTosClient().putObject(putRequest);
            if (Objects.isNull(output) || StringUtils.isEmpty(output.getEtag())) {
                log.error("文件上传失败");
                return null;
            }
            // 2. 设置 ACL
            setObjectPublicRead(objectKey);
            TOSRespVO tosRespVO = new TOSRespVO();
            tosRespVO.setObjectKey(objectKey);
            tosRespVO.setAccessUrl(generateFileUrl(objectKey));
            return tosRespVO;
        } catch (TosClientException e) {
            log.error("客户端参数错误: {}", e.getMessage());
        } catch (TosServerException e) {
            log.error("服务端响应异常 [{}] {}", e.getStatusCode(), e.getCode());
        } catch (Exception e) {
            log.error("系统异常: {}", e.getMessage());
        }
        return null;
    }

    public void deleteFile(String objectKey) {
        try {
            // 1. 上传文件
            DeleteObjectInput delRequest = new DeleteObjectInput()
                    .setBucket(BUCKET)
                    .setKey(objectKey);

            getTosClient().deleteObject(delRequest);
        } catch (TosClientException e) {
            log.error("客户端参数错误: {}", e.getMessage());
        } catch (TosServerException e) {
            log.error("服务端响应异常 [{}] {}", e.getStatusCode(), e.getCode());
        } catch (Exception e) {
            log.error("系统异常: {}", e.getMessage());
        }
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
    public String generateObjectKey(BusinessType businessType, String filename) {
        String dateDir = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return String.format("common/%s/%s/%s/%s",  // 增加环境层级
                ENV,                // 环境变量（dev/test/prod）
                businessType.name(),
                dateDir,
                filename.replaceAll("[^a-zA-Z0-9-_.]", "_"));
    }

    /**
     * 构建文件访问URL
     */
    private String generateFileUrl(String objectKey) {
        return ENDPOINT  + "/" + objectKey;
    }

    public String generatePublicFileUrl(String objectKey) {
        this.setObjectPublicRead(objectKey);
        return ENDPOINT  + "/" + objectKey;
    }

    /**
     * 初始化TOS客户端（双重校验锁单例）
     */
    private TOSV2 getTosClient() {
        if (tosClient == null) {
            synchronized (TOSUpFileUtil.class) {
                TOSClientConfiguration configuration = TOSClientConfiguration.builder()
                        .region(REGION)
                        .endpoint(ENDPOINT)
                        .isCustomDomain(true)   // 标识当前域名为自定义域名
                        .credentials(new StaticCredentials(ACCESS_KEY, ENCODED_SECRET)).build();
                if (tosClient == null) {
                    tosClient = new TOSV2ClientBuilder()
                            .build(configuration);
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
            // 确保客户端已初始化
            TOSV2 client = getTosClient();

            // 将分钟转换为秒
            long expiresSeconds = Duration.ofMinutes(expiryDurationMinutes).getSeconds();

            // 创建预签名URL请求
            PreSignedURLInput input = new PreSignedURLInput()
                    .setHttpMethod(HttpMethod.GET)  // 设置HTTP方法
                    .setBucket(BUCKET)             // 设置存储桶名称
                    .setKey(objectKey)              // 设置对象键
                    .setExpires(expiresSeconds);   // 设置过期时间

            PreSignedURLOutput output = client.preSignedURL(input);
            return output.getSignedUrl(); // 从输出对象中获取URL字符串

        } catch (TosClientException e) {
            log.error("客户端错误: {}", e.getMessage());
        } catch (TosServerException e) {
            log.error("服务端错误: 状态码={}, 错误码={}", e.getStatusCode(), e.getCode());
        } catch (Exception e) {
            log.error("未知错误: {}", e.getMessage());
        }
        return null;
    }
}
