package com.allez.lang.util;

import com.allez.lang.enums.IValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author chenyu
 * @date 2024/3/8 19:04
 * @description 枚举转化工具类
 */
public class EnumConverterUtils {
    private static final Map<Class<? extends IValue<?>>, Map<Object, IValue<?>>> ENUM_TYPE_VALUE_MAP = new ConcurrentHashMap<>();

    /**
     * value转枚举
     * @param enumClass
     * @param value
     * @return
     * @param <T>
     * @param <E>
     */
    @SuppressWarnings("unchecked")
    public static <T, E extends IValue<T>> E convert(Class<E> enumClass, T value) {
        if (Objects.isNull(value)) {
            return null;
        }
        // 取缓存
        Map<Object, IValue<?>> enumValueMap = ENUM_TYPE_VALUE_MAP.get(enumClass);
        if (Objects.isNull(enumValueMap)) {
            // 不存在就解析一下
            synchronized (EnumConverterUtils.class) {
                enumValueMap = ENUM_TYPE_VALUE_MAP.get(enumClass);
                if (Objects.isNull(enumValueMap)) {
                    enumValueMap = new ConcurrentHashMap<>();
                    // 根据value放入缓存
                    for (E enumObj : enumsOf(enumClass)) {
                        enumValueMap.put(enumObj.getValue(), enumObj);
                    }
                    ENUM_TYPE_VALUE_MAP.put(enumClass, enumValueMap);
                }
            }
        }
        return (E) enumValueMap.get(value);
    }

    /**
     * 执行枚举的values方法
     * @param enumClass
     * @return
     * @param <T>
     * @param <E>
     */
    private static <T, E extends IValue<T>> E[] enumsOf(Class<E> enumClass) {

        // 必须是枚举类型
        if (!(Enum.class.isAssignableFrom(enumClass))) {
            throw new RuntimeException(String.format("%s is not a enum type"));
        }
        try {
            // 执行枚举values方法获取所有元素
            Method valuesMethod = enumClass.getMethod("values");
            return  (E[]) valuesMethod.invoke(null);
        } catch (NoSuchMethodException e) {
            // 入口有枚举类型检查, 所以不会执行到这里
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            // 入口有枚举类型检查, 所以不会执行到这里
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            // 入口有枚举类型检查, 所以不会执行到这里
            throw new RuntimeException(e);
        }
    }

    /**
     * 非空的
     * @param enumClass
     * @param value
     * @return
     * @param <T>
     * @param <E>
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
     * @param enumClass
     * @param value
     * @param defaultValue
     * @return
     * @param <T>
     * @param <E>
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
     * @param value
     * @return
     */
    public static <T, E extends IValue<T>> Optional<E> convertOptional(Class<E> enumClass, T value) {
        return Optional.ofNullable(convert(enumClass, value));
    }

    public static <T, E extends IValue<T>> Collection<T> valuesOf(Class<E> enumClass) {
        return Arrays.stream(enumsOf(enumClass)).map(e -> e.getValue()).collect(Collectors.toList());
    }

    public static <T, E extends IValue<T>> boolean contains(Class<E> enumClass, T value) {
        return valuesOf(enumClass).contains(value);
    }
}
