package com.allez.application.entity;

import com.allez.application.wrapper.GlobalHttpServletRequestWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyu
 * @date 2024/7/24 下午5:05
 * @description 一次http请求的详细信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetailInfo implements Serializable {

    private RequestHeaderParam headerParam;

    private String url;

    private String contentType;

    private String method;


    public static RequestDetailInfo of(GlobalHttpServletRequestWrapper httpServletRequest) {
        RequestDetailInfo requestDetailInfo = new RequestDetailInfo();
        requestDetailInfo.setHeaderParam(RequestHeaderParam.of(httpServletRequest));
        requestDetailInfo.setUrl(getUrl(httpServletRequest));
//        requestDetailInfo.setFormDataMap(buildFormDataMap(httpServletRequest));
//        UrlQuery urlQuery = UrlQuery.of(httpServletRequest.getQueryString(), StandardCharsets.UTF_8);
        requestDetailInfo.setMethod(httpServletRequest.getMethod());
        requestDetailInfo.setContentType(httpServletRequest.getContentType());
//        requestDetailInfo.setQueryParamMap(urlQuery.getQueryMap());
        return requestDetailInfo;
    }


    public static Map<String, Object> buildFormDataMap(HttpServletRequest servletRequest) {
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

    public static String getUrl(HttpServletRequest servletRequest) {
        return servletRequest.getContextPath() + servletRequest.getServletPath();
    }


}