package com.allez.application.filter;

import com.allez.application.GlobalRequestContextHolder;
import com.allez.application.entity.RequestDetailInfo;
import com.allez.application.wrapper.GlobalHttpServletRequestWrapper;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author chenyu
 * @date 2024/7/31 下午1:42
 * @description
 */
public class WrapHttpServletRequestFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(httpServletResponse);
        chain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
    }
}
