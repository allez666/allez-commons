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

    public static void main(String[] args) {
        ResultCode.values()
    }




}
