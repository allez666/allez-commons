package com.allez.application;

import com.allez.application.entity.RequestDetailInfo;
import com.allez.application.wrapper.GlobalHttpServletRequestWrapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @author chenyu
 * @date 2024/7/24 下午5:16
 * @description 全局请求上下文
 */
public class GlobalRequestContextHolder {

    private static final ThreadLocal<RequestDetailInfo> REQUEST_DETAIL_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static void setRequestDetailInfo(RequestDetailInfo requestDetailInfo) {
        REQUEST_DETAIL_INFO_THREAD_LOCAL.set(requestDetailInfo);
    }

    public static RequestDetailInfo getRequestDetailInfo() {
        return REQUEST_DETAIL_INFO_THREAD_LOCAL.get();
    }

    public static void removeRequestDetailInfo() {
        REQUEST_DETAIL_INFO_THREAD_LOCAL.remove();
    }


    public static GlobalHttpServletRequestWrapper getServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("RequestContextHolder is not set");
        }
        return (GlobalHttpServletRequestWrapper) attributes.getRequest();
    }

    public static HttpServletResponse getServletResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("RequestContextHolder is not set");
        }
        return attributes.getResponse();
    }

    public static void clear() {
        REQUEST_DETAIL_INFO_THREAD_LOCAL.remove();
    }


}
