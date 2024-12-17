package com.allez.application.controller;

import org.springframework.web.bind.annotation.*;

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
