package com.allez.lang.util;

import com.allez.lang.enums.BoooleanEnum;
import com.allez.lang.enums.IValue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenyu
 * @date 2024/3/8 19:04
 * @description 枚举转化工具类
 */
public class EnumConverterUtils {
    /**
     * enumClass -> (value-> enum)
     */
    private static final Map<Class<? extends IValue<?>>, Map<?, ?>> ENUM_TYPE_VALUE_MAP = new ConcurrentHashMap<>();


    /**
     * 获取缓存map
     * 将泛型擦除都收敛在这里
     */
    @SuppressWarnings("unchecked")
    private static <T, E extends IValue<T>> Map<T, E> getCacheMap(Class<E> enumClass) {
        Map<?, ?> map = ENUM_TYPE_VALUE_MAP
                .computeIfAbsent(enumClass, EnumConverterUtils::buildValueMap);
        return (Map<T, E>) map;
    }

    private static Map<?, ?> buildValueMap(Class<? extends IValue<?>> enumClass) {
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("Not an enum: " + enumClass);
        }
        IValue<?>[] enums = enumClass.getEnumConstants();
        Map<Object, Object> map = new HashMap<>(enums.length);

        for (IValue<?> e : enums) {
            map.put(e.getValue(), e);
        }
        return map;

    }


    public static <T, E extends IValue<T>> E convert(Class<E> enumClass, T value) {
        if (value == null) {
            return null;
        }
        return getCacheMap(enumClass).get(value);
    }

    /**
     * 非空的
     */
    public static <T, E extends IValue<T>> E convertNonNull(Class<E> enumClass, T value) {
        E result = convert(enumClass, value);
        if (Objects.isNull(result)) {
            throw new IllegalArgumentException(String.format("Cannot found enum[%s] by value:%s", enumClass, value));
        }
        return result;
    }

    /**
     * 带默认值的
     */
    public static <T, E extends IValue<T>> E convert(Class<E> enumClass, T value, E defaultValue) {
        E result = convert(enumClass, value);
        if (Objects.isNull(result)) {
            return defaultValue;
        }
        return result;
    }

    /**
     * Optional
     */
    public static <T, E extends IValue<T>> Optional<E> convertOptional(Class<E> enumClass, T value) {
        return Optional.ofNullable(convert(enumClass, value));
    }

    public static <T, E extends IValue<T>> Collection<T> valuesOf(Class<E> enumClass) {
        return new ArrayList<>(getCacheMap(enumClass).keySet());
    }

    public static <T, E extends IValue<T>> boolean contains(Class<E> enumClass, T value) {
        if (value == null) {
            return false;
        }
        return getCacheMap(enumClass).containsKey(value);
    }

    public static void main(String[] args) {
        BoooleanEnum convert = EnumConverterUtils.convert(BoooleanEnum.class, 1);
        System.out.println(1);
    }
}
