package com.allez.application.handler;

import com.allez.lang.entity.Result;
import com.allez.lang.enums.ResultCode;
import com.allez.lang.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: chenyu
 * @create: 2024-07-23 01:12
 * @Description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException businessException) {
        log.error("businessException", businessException);
        return Result.remind(businessException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("exception", e);
        return Result.fail(ResultCode.FAIL, null);
    }


}
