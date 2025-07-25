package com.allez.application.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.url.UrlQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenyu
 * @date 2024/8/20 17:44
 * @description 解析 HttpServletRequest 的工具类
 */
public class HttpServletRequestParseUtil {

    public static String parseUrl(HttpServletRequest servletRequest) {
        return servletRequest.getContextPath() + servletRequest.getServletPath();
    }


    public static boolean isMultipartFile(Part part) {
        String headerValue = part.getHeader(HttpHeaders.CONTENT_DISPOSITION);
        ContentDisposition disposition = ContentDisposition.parse(headerValue);
        String filename = disposition.getFilename();
        return Objects.nonNull(filename);
    }

    public static boolean isFormSubmitted(HttpServletRequest servletRequest) {
        return StringUtils.startsWithIgnoreCase(servletRequest.getContentType(), ("multipart/"));
    }

    public static Map<String, Object> parseFormData(HttpServletRequest servletRequest) {
        Map<String, Object> map = new HashMap<>();
        if (!isFormSubmitted(servletRequest)) {
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


    public static <T> T parseHeader(HttpServletRequest servletRequest, Class<T> clazz) {
        Map<String, String> stringStringMap = parseHeader(servletRequest);
//        return JSON.parseObject(JSON.toJSONString(stringStringMap), clazz);
        return null;
    }

    public static Map<String, String> parseUrlParam(HttpServletRequest servletRequest) {
        Map<String, String> map = new HashMap<>();
        String queryString = servletRequest.getQueryString();
        UrlQuery urlQuery = UrlQuery.of(queryString, StandardCharsets.UTF_8);
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
