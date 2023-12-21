package com.allez.lang.enums;

/**
 * @author chenyu
 * @date 2023/12/21 19:56
 * @description
 */
public interface IValue <T>{

    T getValue();

    default boolean equalsByValue(T value) {
        return value.equals(getValue());
    }
}
