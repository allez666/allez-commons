package com.allez.application.annotation;

import com.allez.application.condition.OnAnnotationCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author: chenGuanXi
 * @create: 2024-12-17 21:58
 * @Description:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnAnnotationCondition.class})
public @interface ConditionalOnAnnotation {

    String valueFieldName = "value";

    Class<? extends Annotation>[] value();

}
