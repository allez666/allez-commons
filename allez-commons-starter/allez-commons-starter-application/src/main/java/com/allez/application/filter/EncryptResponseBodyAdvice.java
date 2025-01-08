package com.allez.application.filter;

import com.alibaba.fastjson.JSON;
import com.allez.application.utils.XORUtil;
import com.allez.lang.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: chenyu
 * @create: 2025-01-09 00:24
 * @Description:
 */
@ControllerAdvice
@Slf4j
@Order(1)
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!(body instanceof Result)) {
            return body;
        }
        Result<?> result = (Result<?>) body;
        return result.mapTo(param -> XORUtil.encryptAndBase64(JSON.toJSONString(param), "709394"));
    }
}
