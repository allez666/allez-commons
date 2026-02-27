package com.allez.application.annotation;

import com.allez.application.condition.OnAnnotationCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author: chenyu
 * @create: 2024-12-17 25:58
 * @Description:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnAnnotationCondition.class})
public @interface ConditionalOnAnnotation {

    String VALUE_FIELD_NAME = "value";

    Class<? extends Annotation>[] value() default {};

}
