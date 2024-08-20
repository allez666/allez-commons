package com.allez.application.config;

import org.springframework.core.Ordered;

/**
 * @author chenyu
 * @date 2024/8/20 13:46
 * @description 自定义过滤器顺序定义
 */
public class FilterOrderConfig {

    public static final Integer TRACE_ID_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE;

    public static final Integer WRAP_HTTP_SERVLET_REQUEST_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE + 1;

    public static final Integer REQUEST_PARAM_ENCRYPT_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE + 2;


}
