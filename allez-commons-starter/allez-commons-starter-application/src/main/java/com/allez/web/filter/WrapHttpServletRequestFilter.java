package com.allez.web.filter;

import com.allez.web.GlobalRequestContextHolder;
import com.allez.web.entity.RequestDetailInfo;
import com.allez.web.wrapper.GlobalHttpServletRequestWrapper;
import com.google.gson.Gson;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

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
//        Collection<Part> parts1 = httpRequest.getParts();
//        httpRequest.getParameterMap();
        GlobalHttpServletRequestWrapper globalHttpServletRequestWrapper = new GlobalHttpServletRequestWrapper(httpRequest);
        try {
            Collection<Part> parts = globalHttpServletRequestWrapper.getParts();
            Map<String, String[]> parameterMap = globalHttpServletRequestWrapper.getParameterMap();
            RequestDetailInfo requestDetailInfo = RequestDetailInfo.of(globalHttpServletRequestWrapper);
            GlobalRequestContextHolder.setRequestDetailInfo(requestDetailInfo);
            chain.doFilter(globalHttpServletRequestWrapper, response);
        } finally {
            GlobalRequestContextHolder.clear();
        }
    }
}
