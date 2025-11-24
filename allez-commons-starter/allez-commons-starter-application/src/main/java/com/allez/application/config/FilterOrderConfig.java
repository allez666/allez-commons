package com.allez.application.config;

import org.springframework.core.Ordered;

/**
 * 自定义过滤器顺序定义
 * <p>
 * 过滤器执行顺序：
 * 1. TraceIdFilter - 生成请求追踪ID
 * 2. CorsFilter - 处理跨域请求
 * 3. ContentCachingFilter - 缓存请求/响应体
 * 4. DecryptRequestParamFilter - 解密请求参数
 *
 * @author chenyu
 * @date 2024/8/20 13:46
 */
public class FilterOrderConfig {

    /**
     * 追踪ID过滤器 - 最高优先级
     */
    public static final Integer TRACE_ID_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE;

    /**
     * 跨域过滤器 - 需要在请求处理前添加CORS头
     */
    public static final Integer CORS_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE + 1;

    /**
     * 请求/响应体缓存过滤器
     */
    public static final Integer CACHE_CONTENT_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE + 2;

    /**
     * 请求参数解密过滤器
     */
    public static final Integer REQUEST_PARAM_ENCRYPT_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE + 3;

}
