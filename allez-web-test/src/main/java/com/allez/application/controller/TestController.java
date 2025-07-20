package com.allez.application.controller;

import com.allez.lang.entity.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author chenyu
 * @date 2024/7/23 下午8:02
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/mmm")
    public String mmm(@RequestParam("a") Integer a,HttpServletRequest httpServletRequest) {
        return "mmm";
    }

    @PostMapping("{id}/hello")
    public String hello(TestReq req, @PathVariable("id") long id, HttpServletRequest httpServletRequest) {
//        RequestDetailInfo requestDetailInfo = RequestDetailInfo.of();
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        return "Hello world!";
    }

    @PostMapping("/test")
    public String test(TestReq testReq) {
        System.out.println(1);
        return "111";
    }

    @GetMapping("/testGet")
    public Result<Map<String,String>> testGet(TestReq req) {
        return Result.success(Map.of("aaa","111","bbb","222"));
    }

    @PostMapping("/testPost")
    public Result<String>testPost(@RequestBody TestReq req) {
        return null;
    }

    @PostMapping("/testForm")
    public Result<String> testForm(TestReq req) {
        String path = "C:\\Users\\40143\\Desktop\\test_upload";
        MultipartFile file = req.getCcc();
        try {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String fileName = file.getOriginalFilename();
            File targetFile = new File(path + File.separator + System.currentTimeMillis() + fileName);
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(null);
    }

}
