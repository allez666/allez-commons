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
        //将写入的数据缓存起来了，而没有同时写到OutputStream中。这导致返回结果一直为空。
        // 解决方法是，在最后一定要手动调用它的copyBodyToResponse方法，将缓存的数据写入输出流里。
        //调用完copyBodyToResponse后，缓存又被清空了，因此getContentAsByteArray一定要在之前调用，才能获取到缓存的返回体。
        contentCachingResponseWrapper.copyBodyToResponse();
    }
}
