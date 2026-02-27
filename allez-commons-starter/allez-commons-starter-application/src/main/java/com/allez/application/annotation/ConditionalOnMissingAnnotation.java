package com.allez.application.annotation;

import com.allez.application.condition.OnAnnotationCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author chenyu
 * @date 2024/12/18 14:00
 * @description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnAnnotationCondition.class})
public @interface ConditionalOnMissingAnnotation {
    String VALUE_FIELD_NAME = "value";

    Class<? extends Annotation>[] value() default {};

}
