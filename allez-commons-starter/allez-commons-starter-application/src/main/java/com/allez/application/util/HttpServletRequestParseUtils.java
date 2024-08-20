package com.allez.application.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.url.UrlQuery;
import com.alibaba.fastjson2.JSON;
import com.allez.application.GlobalRequestContextHolder;
import com.allez.application.wrapper.GlobalHttpServletRequestWrapper;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyu
 * @date 2024/8/20 17:44
 * @description 解析 HttpServletRequest 的工具类
 */
public class HttpServletRequestParseUtils {

    public static String parseUrl(HttpServletRequest servletRequest) {
        return servletRequest.getContextPath() + servletRequest.getServletPath();
    }

    public static String parseUrl() {
        GlobalHttpServletRequestWrapper servletRequest = GlobalRequestContextHolder.getServletRequest();
        return parseUrl(servletRequest);
    }

    public static Map<String, Object> parseFormData() {
        GlobalHttpServletRequestWrapper servletRequest = GlobalRequestContextHolder.getServletRequest();
        return parseFormData(servletRequest);
    }

    public static <T> T parseFormData(Class<T> clazz) {
        GlobalHttpServletRequestWrapper servletRequest = GlobalRequestContextHolder.getServletRequest();
        Map<String, Object> stringObjectMap = parseFormData(servletRequest);
        return JSON.parseObject(JSON.toJSONString(stringObjectMap), clazz);
    }

    public static Map<String, Object> parseFormData(HttpServletRequest servletRequest) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.startsWithIgnoreCase(servletRequest.getContentType(), ("multipart/"))) {
            return map;
        }
        try {
            for (Part part : servletRequest.getParts()) {
                String name = part.getName();
                String parameter;
                String headerValue = part.getHeader(HttpHeaders.CONTENT_DISPOSITION);
                ContentDisposition disposition = ContentDisposition.parse(headerValue);
                String filename = disposition.getFilename();
                if (filename != null) {
                    parameter = filename;
                } else {
                    parameter = servletRequest.getParameter(name);
                }
                map.put(name, parameter);
            }
            return map;
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> parseHeader(HttpServletRequest servletRequest) {
        Map<String, String> headerMap = new HashMap<>();

        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, servletRequest.getHeader(headerName));
        }
        return headerMap;
    }

    public static Map<String, String> parseHeader() {
        GlobalHttpServletRequestWrapper servletRequest = GlobalRequestContextHolder.getServletRequest();
        return parseHeader(servletRequest);
    }

    public static <T> T parseHeader(HttpServletRequest servletRequest, Class<T> clazz) {
        Map<String, String> stringStringMap = parseHeader(servletRequest);
        return JSON.parseObject(JSON.toJSONString(stringStringMap), clazz);
    }

    public static Map<String, String> parseUrlParam(HttpServletRequest servletRequest) {
        Map<String, String> map = new HashMap<>();

        UrlQuery urlQuery = UrlQuery.of(servletRequest.getQueryString(), StandardCharsets.UTF_8);
        Map<CharSequence, CharSequence> queryMap = urlQuery.getQueryMap();
        if (CollUtil.isEmpty(queryMap)) {
            return map;
        }
        for (Map.Entry<CharSequence, CharSequence> entry : queryMap.entrySet()) {
            map.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return map;
    }
}
