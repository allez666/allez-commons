package com.allez.lang.exception;

import com.allez.lang.enums.ResultCode;
import lombok.Getter;

/**
 * @author chenyu
 * @date 2024/12/31 15:59
 * @description
 */
@Getter
public class SystemException extends RuntimeException {
    private final Integer code;

    public SystemException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public SystemException(String msg) {
        super(msg);
        this.code = ResultCode.FAIL.getCode();
    }
}
