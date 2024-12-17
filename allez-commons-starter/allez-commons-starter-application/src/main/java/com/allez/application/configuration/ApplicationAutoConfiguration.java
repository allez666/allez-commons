package com.allez.application.configuration;

import com.allez.application.filter.ContentCachingRequestFilter;
import com.allez.application.filter.DecryptRequestParamFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author chenyu
 * @date 2024/7/24 下午3:24
 * @description
 */
public class ApplicationAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(ContentCachingRequestFilter.class)
    public ContentCachingRequestFilter contentCachingRequestFilter() {
        return new ContentCachingRequestFilter();
    }
    // todo http traceId filter

    // todo 可控加密 ,实现 @ConditionalOnAnnotation

    @Bean
    public DecryptRequestParamFilter decryptRequestParamFilter() {
        return new DecryptRequestParamFilter();
    }


}
