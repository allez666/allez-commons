package com.allez.application.controller;

import com.allez.web.entity.RequestDetailInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenyu
 * @date 2024/7/23 下午8:02
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/hello")
    public String hello(HttpServletRequest servletRequest) {
        RequestDetailInfo requestDetailInfo = RequestDetailInfo.of(servletRequest);
        return "Hello world!";
    }

}
