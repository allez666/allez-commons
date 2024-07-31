package com.allez.web.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author chenyu
 * @date 2024/7/31 下午2:46
 * @description
 */
@Order(-1000)
@WebFilter(urlPatterns = "/*", filterName = "traceIdFilter")
@Component
public class TraceIdFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}
