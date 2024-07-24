package com.allez.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author chenyu
 * @date 2024/7/24 下午5:05
 * @description 一次http请求的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestInfo implements Serializable {

    private RequestHeaderParam headerParam;

    private String url;

    private Map<String,Object> formDataMap;

    private Map<String,Object> queryParamMap;

    private String body;

}
