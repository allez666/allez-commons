package com.allez.web;

import com.allez.web.entity.RequestHeaderParam;
import com.allez.web.entity.RequestDetailInfo;
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




}
