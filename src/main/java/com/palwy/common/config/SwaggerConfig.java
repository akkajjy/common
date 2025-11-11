package com.palwy.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableKnife4j
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 使用or条件组合多个包扫描
                .apis(RequestHandlerSelectors.basePackage("com.palwy.common.controller")
                        .or(RequestHandlerSelectors.basePackage("com.palwy.common.risk.controller")))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(BasicErrorController.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统接口文档")
                .description("CLR核心服务API文档")
                .version("1.0.0")
                .build();
    }
}