package com.allez.application.annotation;


import java.lang.annotation.*;

/**
 * @author chenyu
 * @date 2024/8/20 10:40
 * @description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAnnotataion {
}
