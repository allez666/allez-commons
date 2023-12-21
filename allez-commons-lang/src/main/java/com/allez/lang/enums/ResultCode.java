package com.allez.lang.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenyu
 * @date 2023/12/21 19:46
 * @description
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultCode implements IValue<Integer> {

    SUCCESS(200, "success"),
    FAIL(-1, "fail"),

    REMIND(-2, "remind"),
    ;

    private final int code;

    private final String msg;

    @Override
    public Integer getValue() {
        return null;
    }
}
