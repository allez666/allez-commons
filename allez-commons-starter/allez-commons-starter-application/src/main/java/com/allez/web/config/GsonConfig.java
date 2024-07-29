package com.allez.web.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: chenGuanXi
 * @create: 2024-07-27 22:03
 * @Description:
 */
@Configuration
public class GsonConfig {


    @Bean("gson")
    public Gson getGson() {
        return new Gson();
    }

}
