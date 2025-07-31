package com.palwy.common.config;

import com.alibaba.fastjson2.JSONObject;
import com.ksvip.next.components.core.bean.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 类HttpAspect.java的实现描述
 */
@Aspect
@Component
public class WebAspect {
    private final Logger logger = LoggerFactory.getLogger(WebAspect.class);

    /**
     * 定义AOP扫描路径 第一个注解只扫描aopTest方法
     */
    @Pointcut("execution(* com.palwy.common.controller..*.*(..))")
    public void aspect() {
        if (this.logger.isDebugEnabled()) {
            this.logger.info("debug aop");
        }
    }

    /**
     * @param joinPoint
     * @return
     */
    @Around(value = "aspect()")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if(attributes==null){
            return joinPoint.proceed();
        }
        final HttpServletRequest request = attributes.getRequest();
        //method
        this.logger.info("method={}", request.getMethod());
        //ip
        this.logger.info("ip={}", request.getRemoteAddr());
        //类方法
        this.logger.info("class={} and method name ={}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        //参数 FileAppender
        this.logger.info("参数={}", JSONObject.toJSONString(joinPoint.getArgs()));
        Object result = null;
        final long start = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (final Throwable e) {
            this.logger.info("HttpAspect拦截器error", e);
            final Result res = new Result();
            res.setCode("200001");
            res.setMsg("SYSTEM_ERROR");
            res.setSuccess(false);
        }
        final long end = System.currentTimeMillis();
        this.logger.info("url={} cost time={} ms", request.getRequestURL(), end - start);
        return result;

    }

}
