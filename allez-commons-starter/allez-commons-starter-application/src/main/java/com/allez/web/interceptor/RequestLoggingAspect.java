package com.allez.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author chenyu
 * @date 2024/7/23 下午3:26
 * @description
 */
@Slf4j
@Aspect
public class RequestLoggingAspect {


    @Pointcut(value = "execution(* com.allez..controller..*.*(..))" +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)" )
    private void controllerPointCut(){
    }


    @Around("controllerPointCut()")
    public Object handleControllerAround(ProceedingJoinPoint pjp){

    }
}
