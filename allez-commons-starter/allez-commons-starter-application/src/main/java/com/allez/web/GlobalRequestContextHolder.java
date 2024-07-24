package com.allez.web;

import com.allez.web.entity.RequestHeaderParam;
import com.allez.web.entity.RequestInfo;
import com.google.gson.Gson;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenyu
 * @date 2024/7/24 下午5:16
 * @description 全局请求上下文
 */
public class GlobalRequestContextHolder {

    public static HttpServletRequest getServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }

    public static HttpServletResponse getServletResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getResponse();
    }

    public static RequestInfo getRequestInfo() {
        HttpServletRequest servletRequest = getServletRequest();
        String url = String.format("%s/%s", servletRequest.getContextPath(), servletRequest.getServletPath());

        RequestHeaderParam headerParam = getHeaderParam(servletRequest);


        return null;
    }


    public static RequestHeaderParam getHeaderParam(HttpServletRequest servletRequest) {
        return null;
    }

    public static String getVersion() {
        return getHeaderParam().getVersion();
    }

    public static String getIp() {
        return getHeaderParam().getIp();
    }

    public static String getToken() {
        return getHeaderParam().getToken();
    }

    public static String getSign() {
        return getHeaderParam().getSign();
    }

    public static String getContentType() {
        return getHeaderParam().getContentType();
    }

    public static String getUrl() {
        return getRequestInfo().getUrl();
    }

    public static String getBody() {
        return getRequestInfo().getBody();
    }

    public static <T> T getBody(Class<T> clazz) {
        return new Gson().fromJson(getBody(), clazz);
    }

    public static <T> T getQueryParam(String key) {
        return (T) getRequestInfo().getQueryParamMap().get(key);
    }

    public static <T> T getFormData(String key){
        return (T) getRequestInfo().getFormDataMap().get(key);
    }

}
