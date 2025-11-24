package com.allez.application.configuration;

import com.allez.application.filter.ContentCachingRequestFilter;
import com.allez.application.filter.CorsFilter;
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

    @Bean
    @ConditionalOnMissingBean(CorsFilter.class)
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

    // todo http traceId filter

//    @Bean
//    @ConditionalOnAnnotation({EnableDecryptRequestParam.class})
//    public DecryptRequestParamFilter decryptRequestParamFilter() {
//        return new DecryptRequestParamFilter();
//    }


}
