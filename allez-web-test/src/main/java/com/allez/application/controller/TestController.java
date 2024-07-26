package com.allez.application.controller;

import com.allez.web.entity.RequestDetailInfo;
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
    public String hello(HttpServletRequest servletRequest, TestReq req, @PathVariable("id") long id) {
        RequestDetailInfo requestDetailInfo = RequestDetailInfo.of(servletRequest);
        return "Hello world!";
    }

}
