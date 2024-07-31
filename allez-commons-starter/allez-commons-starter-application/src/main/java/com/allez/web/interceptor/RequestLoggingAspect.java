package com.allez.web.interceptor;

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.util.StrUtil;
import com.allez.web.GlobalRequestContextHolder;
import com.allez.web.entity.RequestDetailInfo;
import com.allez.web.wrapper.GlobalHttpServletRequestWrapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenyu
 * @date 2024/7/23 下午3:26
 * @description
 */
@Slf4j
@Aspect
@Component
public class RequestLoggingAspect {

    @Resource
    private Gson gson;


    @Pointcut(value = "execution(* com.allez..controller..*.*(..))" +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void controllerPointCut() {
    }


    @Around("controllerPointCut()")
    public Object handleControllerAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = SystemClock.now();

        GlobalHttpServletRequestWrapper servletRequest = GlobalRequestContextHolder.getServletRequest();

        RequestDetailInfo requestDetailInfo = RequestDetailInfo.of(servletRequest);
        Object proceed = pjp.proceed();
        log.info("requestDetailInfo:{} \n rt:{} response:{}", gson.toJson(requestDetailInfo), SystemClock.now() - startTime, gson.toJson(proceed));
        return proceed;
    }
}
