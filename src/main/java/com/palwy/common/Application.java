package com.palwy.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * 项目启动类
 */
@Slf4j
@EnableDiscoveryClient // 注册服务到服务注册中心
@SpringBootApplication
@ImportResource("classpath:beanRefContext.xml")
@RefreshScope
@MapperScan("com.palwy.common.mapper")
@ComponentScan(basePackages = {
        "com.palwy.bytehouse",
        "com.palwy.common",
        "com.palwy.common.mapper",
        "com.palwy.common.controller" // 新增控制器包路径
})
public class Application {

    /**
     * 项目启动类
     * @param args 启动参数
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        StopWatch sw = StopWatch.createStarted();
        ConfigurableEnvironment env = SpringApplication.run(Application.class, args).getEnvironment();
        String test = env.getProperty("nan.test");
        String serverPort = env.getProperty("server.port");
        InetAddress ip4 = Inet4Address.getLocalHost();
        String hostAddress = ip4.getHostAddress();
        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application: '{}' is running Success! \n\t" + "Local URL: \thttp://{}:{}\n\t"
                        + "Document:\thttp://{}:{}/doc.html\n"
                        + "----------------------------------------------------------",
                env.getProperty("spring.application.name"), hostAddress,serverPort,
                hostAddress,serverPort);
        log.info(">>>>>ip:{},cose time ={} ms{}", hostAddress, sw.getTime(),test);
    }

    /***
     * 由于 actuator 会暴露诸多接口(/beans,/configprops等),导致安全问题. 因此默认不使用 actuator
     * ,自行定义健康检查接口
     *
     * @return
     */
    @RequestMapping("/health")
    public String health() {
        return "UP";
    }

}