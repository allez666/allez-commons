package com.allez.application.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author chenyu
 * @date 2024/7/23 下午8:02
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("{id}/hello")
    public String hello(TestReq req, @PathVariable("id") long id, HttpServletRequest httpServletRequest) {
//        RequestDetailInfo requestDetailInfo = RequestDetailInfo.of();
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        return "Hello world!";
    }

}
