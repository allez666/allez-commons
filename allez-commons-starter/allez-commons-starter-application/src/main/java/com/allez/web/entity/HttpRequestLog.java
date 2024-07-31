package com.allez.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author: chenGuanXi
 * @create: 2024-08-01 01:39
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpRequestLog {

    private String url;

    private Map<String, Object> paramMap;

    private Object result;

    private long rt;

}
