package com.allez.application;

import com.allez.application.annotation.EnableDecryptRequestParam;
import com.allez.application.annotation.TestAnnotataion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyu
 * @date 2024/7/23 下午3:39
 * @description
 */
@SpringBootApplication
@EnableDecryptRequestParam
//@TestAnnotataion
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
