package com.allez.lang.exception;

import com.allez.lang.enums.ResultCode;
import lombok.*;

/**
 * @author chenyu
 * @date 2023/12/21 19:24
 * @description
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;


    public BusinessException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(Integer code) {
        super(ResultCode.FAIL.getMsg());
        this.code = code;
    }

    public BusinessException(String msg){
        super(msg);
        this.code = ResultCode.FAIL.getCode();
    }

    public BusinessException(ResultCode code) {
        super(code.getMsg());
        this.code = code.getCode();
    }

    public BusinessException(ResultCode code, String message) {
        super(message);
        this.code = code.getCode();
    }


}
