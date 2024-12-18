package com.allez.application.enums;

import com.allez.lang.enums.IValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenyu
 * @date 2024/12/18 14:29
 * @description 客户端类型
 */
@Getter
@AllArgsConstructor
public enum ClientTypeEnum implements IValue<Integer> {

    H5(1, "H5"),
    IOS(2, "IOS"),
    ANDROID(3, "ANDROID"),

    ;

    private final Integer value;

    private final String desc;
}
