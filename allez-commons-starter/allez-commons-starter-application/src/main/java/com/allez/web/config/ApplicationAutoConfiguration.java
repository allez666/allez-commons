package com.allez.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author chenyu
 * @date 2024/7/24 下午3:24
 * @description
 */
@ComponentScan(basePackages = "com.allez.web.*")
public class ApplicationAutoConfiguration {
}
