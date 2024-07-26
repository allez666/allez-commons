package com.allez.application.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenyu
 * @date 2024/7/26 下午5:35
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestReq {

    private String aaa;
    private String bbb;

    private String ccc;

    private String ddd;

    private MultipartFile file;


}
