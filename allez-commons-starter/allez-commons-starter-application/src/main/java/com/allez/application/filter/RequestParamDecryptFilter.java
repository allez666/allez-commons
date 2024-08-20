package com.allez.application.filter;

import com.allez.application.GlobalRequestContextHolder;
import com.allez.application.entity.RequestDetailInfo;
import com.allez.application.util.HttpServletRequestParseUtils;
import com.allez.application.wrapper.HttpServletDecryptRequestParamWrapper;
import com.allez.lang.enums.BoooleanEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author chenyu
 * @date 2024/8/20 10:38
 * @description
 */
public class RequestParamDecryptFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        RequestDetailInfo requestDetailInfo = GlobalRequestContextHolder.getRequestDetailInfo();

        // 是否要解密参数
        boolean decryptRequestParam = requestDetailInfo.getHeaderParam().getDecryptRequestParam();
        if (BoooleanEnum.FALSE.equalsByBoolValue(decryptRequestParam)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletDecryptRequestParamWrapper requestParamDecryptFilter = new HttpServletDecryptRequestParamWrapper(httpRequest);
        Map<String, Object> stringObjectMap = HttpServletRequestParseUtils.parseFormData(requestParamDecryptFilter);
        chain.doFilter(request, response);
    }
}
