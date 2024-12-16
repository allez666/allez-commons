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
        //将写入的数据缓存起来了，而没有同时写到OutputStream中。这导致返回结果一直为空。
        // 解决方法是，在最后一定要手动调用它的copyBodyToResponse方法，将缓存的数据写入输出流里。
        //调用完copyBodyToResponse后，缓存又被清空了，因此getContentAsByteArray一定要在之前调用，才能获取到缓存的返回体。
        contentCachingResponseWrapper.copyBodyToResponse();
    }
}
