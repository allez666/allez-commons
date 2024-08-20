package com.allez.application.interceptor;

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.util.ArrayUtil;
import com.allez.application.GlobalRequestContextHolder;
import com.allez.application.entity.HttpRequestLog;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

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

    private final Predicate<Object> logPredicate = e -> !(e instanceof HttpServletRequest || e instanceof HttpServletResponse);


    @Pointcut(value = "execution(* com.allez..controller..*.*(..))" +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void controllerPointCut() {
    }


    @Around("controllerPointCut()")
    public Object handleControllerAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = SystemClock.now();

        Map<String, Object> paramMap = buildLogArgs(pjp);
        Object result = pjp.proceed();

        Object logResult = Objects.isNull(result) || !logPredicate.test(result) ? null : result;


        HttpRequestLog requestLog = HttpRequestLog
                .builder()
                .url(GlobalRequestContextHolder.getRequestDetailInfo().getUrl())
                .rt(SystemClock.now() - startTime)
                .paramMap(paramMap)
                .result(logResult)
                .build();

        log.info("requestLog:{}", gson.toJson(requestLog));
        return result;
    }

    private Map<String, Object> buildLogArgs(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        if (ArrayUtil.isEmpty(args)) {
            return new HashMap<>();
        }
        String[] parameterNames = ((MethodSignature) pjp.getSignature()).getParameterNames();

        Map<String, Object> paramMap = new HashMap<>(args.length);


        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            Object arg = args[i];
            if (logPredicate.test(arg)) {
                paramMap.put(parameterName, arg);
            }
        }

        return paramMap;
    }
}
