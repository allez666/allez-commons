package com.allez.web.filter;

import com.allez.web.wrapper.GlobalHttpServletRequestWrapper;
import com.google.gson.Gson;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author chenyu
 * @date 2024/7/31 下午1:42
 * @description
 */
@Order(-999)
@WebFilter(urlPatterns = "/*", filterName = "wrapHttpServletRequestFilter")
@Component
public class WrapHttpServletRequestFilter implements Filter {

    @Resource
    private Gson gson;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        request = new GlobalHttpServletRequestWrapper(httpRequest);
        chain.doFilter(request, response);
    }
}
