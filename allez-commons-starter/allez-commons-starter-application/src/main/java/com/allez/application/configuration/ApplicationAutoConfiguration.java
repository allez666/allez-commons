package com.allez.application.configuration;

import com.allez.application.config.FilterOrderConfig;
import com.allez.application.filter.ContentCachingRequestFilter;
import com.allez.application.filter.WrapHttpServletRequestFilter;
import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

/**
 * @author chenyu
 * @date 2024/7/24 下午3:24
 * @description
 */
public class ApplicationAutoConfiguration {


    /**
     * 全局的gson bean
     * 业务系统可以自定义
     */
    @Bean("gson")
    @ConditionalOnMissingBean(Gson.class)
    public Gson getGson() {
        return new Gson();
    }

//    @Bean
    @ConditionalOnMissingBean(ContentCachingRequestFilter.class)
    public ContentCachingRequestFilter contentCachingRequestFilter() {
        return new ContentCachingRequestFilter();
    }

    @Bean
    @ConditionalOnMissingBean(WrapHttpServletRequestFilter.class)
    public FilterRegistrationBean<Filter> wrapHttpServletRequestFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new WrapHttpServletRequestFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(FilterOrderConfig.CACHE_CONTENT_FILTER_ORDER);
        return registrationBean;
    }



}
