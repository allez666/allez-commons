package com.allez.application.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartFileSerializer extends JsonSerializer<MultipartFile> {
    @Override
    public void serialize(MultipartFile value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getOriginalFilename());
    }
}