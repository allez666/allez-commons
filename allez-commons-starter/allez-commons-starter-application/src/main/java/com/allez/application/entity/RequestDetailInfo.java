package com.allez.application.entity;

import com.allez.application.utils.HttpServletRequestParseUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

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
        RequestHeaderParam requestHeaderParam = HttpServletRequestParseUtil.parseHeader(httpServletRequest, RequestHeaderParam.class);
        requestDetailInfo.setHeaderParam(requestHeaderParam);
        requestDetailInfo.setUrl(HttpServletRequestParseUtil.parseUrl(httpServletRequest));
        requestDetailInfo.setMethod(httpServletRequest.getMethod());
        requestDetailInfo.setContentType(httpServletRequest.getContentType());
        return requestDetailInfo;
    }


}
