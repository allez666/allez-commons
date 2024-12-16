package com.allez.application.filter;

import com.allez.application.config.FilterOrderConfig;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenyu
 * @date 2024/12/16 19:28
 * @description
 */
public class ContentCachingRequestFilter extends OncePerRequestFilter implements OrderedFilter {
    @Override
    public int getOrder() {
        return FilterOrderConfig.CACHE_CONTENT_FILTER_ORDER;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
    }
}
