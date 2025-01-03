package com.allez.application.filter;

import com.allez.application.config.FilterOrderConfig;
import com.allez.application.constant.CommonConstant;
import com.allez.application.wrapper.HttpServletDecryptRequestParamWrapper;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: chenyu
 * @create: 2024-12-16 21:55
 * @Description:
 */
public class DecryptRequestParamFilter extends OncePerRequestFilter implements OrderedFilter {



    @Override
    public int getOrder() {
        return FilterOrderConfig.REQUEST_PARAM_ENCRYPT_FILTER_ORDER;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(CommonConstant.NO_DECRYPTION_HEADER_KEY);
        boolean noDecrypt = Objects.equals(header, CommonConstant.NO_DECRYPTION_HEADER_VALUE);
        if (request.getMethod().equals(HttpMethod.OPTIONS.name()) || noDecrypt) {
            filterChain.doFilter(request, response);
            return;
        }
        HttpServletDecryptRequestParamWrapper httpServletDecryptRequestParamWrapper = new HttpServletDecryptRequestParamWrapper(request);
        filterChain.doFilter(httpServletDecryptRequestParamWrapper, response);
    }
}
