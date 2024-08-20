package com.allez.application.configuration;

import com.allez.application.config.FilterOrderConfig;
import com.allez.application.filter.TraceIdFilter;
import com.allez.application.filter.WrapHttpServletRequestFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.Filter;

/**
 * @author chenyu
 * @date 2024/8/20 09:59
 * @description
 */
@Configuration
public class FilterAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(WrapHttpServletRequestFilter.class)
    public FilterRegistrationBean<Filter> wrapHttpServletRequestFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new WrapHttpServletRequestFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(FilterOrderConfig.WRAP_HTTP_SERVLET_REQUEST_FILTER_ORDER);
        return registrationBean;
    }

    @Bean
    @ConditionalOnMissingBean(TraceIdFilter.class)
    public FilterRegistrationBean<Filter> traceIdFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceIdFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(FilterOrderConfig.TRACE_ID_FILTER_ORDER);
        return registrationBean;
    }

}
