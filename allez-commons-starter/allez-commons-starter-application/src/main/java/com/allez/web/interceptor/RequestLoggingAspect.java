package com.allez.web.interceptor;

import cn.hutool.core.date.SystemClock;
import com.allez.web.entity.RequestDetailInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        System.out.println(1);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        StopWatch stopWatch = new StopWatch();

        stopWatch.start("to requestDetailInfo");
        RequestDetailInfo requestDetailInfo = RequestDetailInfo.of(request);
        stopWatch.stop();

        stopWatch.start("invoke");
        Object proceed = pjp.proceed();
        stopWatch.stop();

        log.info("requestDetailInfo:{} \n rt:{}", gson.toJson(requestDetailInfo), stopWatch.prettyPrint());
        return proceed;
    }
}
