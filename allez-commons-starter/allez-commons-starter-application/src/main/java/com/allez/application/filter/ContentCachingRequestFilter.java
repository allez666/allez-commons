package com.allez.application.filter;

import com.allez.application.config.FilterOrderConfig;
import com.allez.application.wrapper.BodyInputStreamWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/**
 * 请求/响应体缓存过滤器
 * 用于缓存 Request Body 和 Response Body，方便后续读取（如日志记录、加解密等）
 *
 * @author chenyu
 * @date 2024/12/16 19:28
 */
public class ContentCachingRequestFilter extends OncePerRequestFilter implements OrderedFilter {

    @Override
    public int getOrder() {
        return FilterOrderConfig.CACHE_CONTENT_FILTER_ORDER;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper contentCachingRequestWrapper = buildContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
        //将写入的数据缓存起来了，而没有同时写到OutputStream中。这导致返回结果一直为空。
        // 解决方法是，在最后一定要手动调用它的copyBodyToResponse方法，将缓存的数据写入输出流里。
        //调用完copyBodyToResponse后，缓存又被清空了，因此getContentAsByteArray一定要在之前调用，才能获取到缓存的返回体。
        contentCachingResponseWrapper.copyBodyToResponse();
    }

    /**
     * 原生的 ContentCachingRequestWrapper 里面缓存了读取的字节流
     * 但是在流读完之后，并没有 从缓存的流中拿，还是会造成二次读取报错
     * 这里重写一下 获取流的方法，读完后 从缓存拿
     */
    private ContentCachingRequestWrapper buildContentCachingRequestWrapper(HttpServletRequest request) {
        return new ContentCachingRequestWrapper(request) {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                ServletInputStream inputStream = super.getInputStream();
                if (inputStream.isFinished()) {
                    return new BodyInputStreamWrapper(this.getContentAsByteArray());
                } else {
                    return inputStream;
                }
            }
        };
    }

}
