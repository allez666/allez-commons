package com.allez.application.configuration;

import com.google.gson.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author: chenyu
 * @create: 2024-07-27 22:03
 * @Description:
 */
@Configuration
public class GsonAutoConfiguration {


    @Bean("gson")
    public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(MultipartFile.class, new MultipartFileLogJsonSerializer())
                .create();
    }

    public static class MultipartFileLogJsonSerializer implements JsonSerializer<MultipartFile> {

        @Override
        public JsonElement serialize(MultipartFile multipartFile, Type type, JsonSerializationContext jsonSerializationContext) {
            if (Objects.isNull(multipartFile)) {
                return null;
            }
            return jsonSerializationContext.serialize(multipartFile.getOriginalFilename());
        }
    }

}
