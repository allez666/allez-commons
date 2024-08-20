package com.allez.application.annotation;


import com.allez.application.configuration.RequestParamDecryptConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author chenyu
 * @date 2024/8/20 10:40
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RequestParamDecryptConfiguration.class)
public @interface EnableDecryptRequestParam {
}
