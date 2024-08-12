package com.allez.application.filter;

import com.allez.application.GlobalRequestContextHolder;
import com.allez.application.entity.RequestDetailInfo;
import com.allez.application.wrapper.GlobalHttpServletRequestWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        GlobalHttpServletRequestWrapper globalHttpServletRequestWrapper = new GlobalHttpServletRequestWrapper(httpRequest);
        try {
            RequestDetailInfo requestDetailInfo = RequestDetailInfo.of(globalHttpServletRequestWrapper);
            GlobalRequestContextHolder.setRequestDetailInfo(requestDetailInfo);
            chain.doFilter(globalHttpServletRequestWrapper, response);
        } finally {
            GlobalRequestContextHolder.clear();
        }
    }
}
