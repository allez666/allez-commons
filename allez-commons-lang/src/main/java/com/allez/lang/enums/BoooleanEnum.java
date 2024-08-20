package com.allez.lang.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author chenyu
 * @date 2023/12/21 20:02
 * @description
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BoooleanEnum implements IValue<Integer> {
    FALSE(0, false, "false"),

    TRUE(1, true, "true"),
    ;

    private final int intValue;

    private final boolean boolValue;

    private final String stringValue;


    @Override
    public Integer getValue() {
        return this.intValue;
    }

    public boolean equalsByIntValue(Integer intValue) {
        return Objects.equals(this.intValue, intValue);
    }

    public boolean equalsByBoolValue(Boolean boolValue) {
        return Objects.equals(this.boolValue, boolValue);
    }

    public boolean equalsByStringValue(String stringValue) {
        return Objects.equals(this.stringValue, stringValue);
    }


    public boolean equalsIgnoreCaseByStringValue(String stringValue) {
        return equalsByStringValue(stringValue) || this.stringValue.equalsIgnoreCase(stringValue);
    }


}
