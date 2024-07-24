package com.allez.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenyu
 * @date 2024/7/24 下午5:03
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestHeaderParam implements Serializable {

    private String version;

    private String ip;

    private String token;

    private String sign;

    private String contentType;
}
