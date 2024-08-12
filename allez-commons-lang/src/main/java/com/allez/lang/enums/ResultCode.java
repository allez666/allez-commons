package com.allez.lang.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenyu
 * @date 2023/12/21 19:46
 * @description  200就是成功，-1失败，-2toast提示
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultCode implements IValue<Integer> {

    SUCCESS(200, "success"),
    FAIL(-1, "fail"),

    /**
     * 提醒
     */
    REMIND(-2, "remind"),
    ;

    private final int code;

    private final String msg;

    @Override
    public Integer getValue() {
        return this.code;
    }
}
