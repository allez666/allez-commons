package com.allez.application.handler;

import com.allez.lang.entity.Result;
import com.allez.lang.enums.ResultCode;
import com.allez.lang.exception.BusinessException;
import com.allez.lang.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

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
        return Objects.isNull(businessException.getCode()) ? Result.remind(businessException.getMessage())
                : Result.of(businessException.getCode(), businessException.getMessage());
    }

    @ExceptionHandler({Exception.class, SystemException.class, RuntimeException.class})
    public Result<Void> handleException(Exception e) {
        log.error("exception", e);
        return Result.fail(ResultCode.FAIL, null);
    }


}
