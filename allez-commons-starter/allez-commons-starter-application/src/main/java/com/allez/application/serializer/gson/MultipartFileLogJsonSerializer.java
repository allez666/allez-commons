package com.allez.application.serializer.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author chenyu
 * @date 2024/7/24 下午3:24
 * @description MultipartFile
 */

public class MultipartFileLogJsonSerializer implements JsonSerializer<MultipartFile> {

        @Override
        public JsonElement serialize(MultipartFile multipartFile, Type type, JsonSerializationContext jsonSerializationContext) {
            if (Objects.isNull(multipartFile)) {
                return null;
            }
            return jsonSerializationContext.serialize(multipartFile.getOriginalFilename());
        }
    }