package com.allez.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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

    private Map<String, Object> formDataMap;

    private Map<String, Object> queryParamMap;

    private String body;

//    private Map<String,Object> path


    public static RequestDetailInfo of(HttpServletRequest httpServletRequest) {
        RequestDetailInfo requestDetailInfo = new RequestDetailInfo();
        requestDetailInfo.setHeaderParam(RequestHeaderParam.of(httpServletRequest));
        requestDetailInfo.setUrl(getUrl(httpServletRequest));
//        requestDetailInfo.setFormDataMap();
//        requestDetailInfo.setQueryParamMap();
//        requestDetailInfo.setBody();
        return requestDetailInfo;
    }

    public static Map<String, Object> buildFormDataMap(HttpServletRequest servletRequest) {
        if (servletRequest instanceof MultipartHttpServletRequest) {
            return new HashMap<>();
        }
//        ((MultipartHttpServletRequest)servletRequest).getparam
        return new HashMap<>();
    }

    public static String getUrl(HttpServletRequest servletRequest) {
        return servletRequest.getContextPath() + servletRequest.getServletPath();
    }


}
