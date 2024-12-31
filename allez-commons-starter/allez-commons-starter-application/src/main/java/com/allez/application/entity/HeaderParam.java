package com.allez.application.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * @author chenyu
 * @date 2024/7/24 下午5:03
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class HeaderParam implements Serializable {


    private ClientInfo clientInfo;

    private String token;

    private String sign;

    private Boolean decryptRequestParam = true;

    private Boolean decryptRequestUrl = true;


    public static HeaderParam of(HttpServletRequest servletRequest) {
        JSONObject jsonObject = new JSONObject();

        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            jsonObject.put(headerName, servletRequest.getHeader(headerName));
        }
        return jsonObject.toJavaObject(HeaderParam.class);
    }
}
