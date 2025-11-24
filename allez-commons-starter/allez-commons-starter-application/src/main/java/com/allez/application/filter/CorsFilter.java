package com.allez.application.filter;

import com.allez.application.config.FilterOrderConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 跨域配置过滤器
 * 处理跨域请求，添加 CORS 响应头
 * todo 待以后有gateway了就把这放到gateway中
 *
 * @author chenyu
 * @date 2025/01/25
 */
public class CorsFilter extends OncePerRequestFilter implements OrderedFilter {

    @Override
    public int getOrder() {
        return FilterOrderConfig.CORS_FILTER_ORDER;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 允许的来源（可配置为具体域名）
        response.setHeader("Access-Control-Allow-Origin", "*");

        // 允许的请求方法
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");

        // 允许的请求头
        response.setHeader("Access-Control-Allow-Headers", "*");

        // 预检请求的有效期（秒）
        response.setHeader("Access-Control-Max-Age", "3600");

        // 是否允许发送Cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 如果是 OPTIONS 预检请求，直接返回成功
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        filterChain.doFilter(request, response);
    }

}