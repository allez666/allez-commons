package com.allez.application.entity;

import cn.hutool.core.net.url.UrlQuery;
import com.allez.application.util.HttpServletRequestParseUtils;
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
import java.nio.charset.StandardCharsets;
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


    public static RequestDetailInfo of(HttpServletRequest httpServletRequest) {
        RequestDetailInfo requestDetailInfo = new RequestDetailInfo();
        RequestHeaderParam requestHeaderParam = HttpServletRequestParseUtils.parseHeader(httpServletRequest, RequestHeaderParam.class);
        requestDetailInfo.setHeaderParam(requestHeaderParam);
        requestDetailInfo.setUrl(HttpServletRequestParseUtils.parseUrl(httpServletRequest));
        requestDetailInfo.setMethod(httpServletRequest.getMethod());
        requestDetailInfo.setContentType(httpServletRequest.getContentType());
        return requestDetailInfo;
    }


}
