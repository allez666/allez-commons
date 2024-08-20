package com.allez.application.configuration;

import com.allez.application.config.FilterOrderConfig;
import com.allez.application.filter.RequestParamDecryptFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author chenyu
 * @date 2024/8/20 11:44
 * @description
 */
@Configuration
public class RequestParamDecryptConfiguration {


    @Bean
    public FilterRegistrationBean<Filter> requestParamEncryptFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestParamDecryptFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(FilterOrderConfig.REQUEST_PARAM_ENCRYPT_FILTER_ORDER);
        return registrationBean;
    }
}
