package com.allez.application.wrapper;

import cn.hutool.core.util.StrUtil;
import com.allez.application.filter.RequestParamDecryptFilter;
import com.allez.application.util.HttpServletRequestParseUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author chenyu
 * @date 2024/8/20 15:54
 * @description 对请求参数进行解密
 */
public class HttpServletDecryptRequestParamWrapper extends GlobalHttpServletRequestWrapper {

    private final Map<String, String> headerMap;

    private final Map<String, String[]> paramMap;

    private final Collection<Part> parts;

    private final String urlQueryString;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public HttpServletDecryptRequestParamWrapper(HttpServletRequest request) {
        super(request);
        this.headerMap = new HashMap<>();
        this.paramMap = decryptParamMap(request);
        this.parts = decryptParts(request);
        this.urlQueryString = request.getQueryString();
    }

    @Override
    public String getQueryString() {
        return this.urlQueryString;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return super.getPart(name);
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return this.parts;
    }

    public Collection<Part> decryptParts(HttpServletRequest request) {
        try {
            Enumeration<String> attributeNames = request.getAttributeNames();
            Collection<Part> requestParts = request.getParts();
            for (Part requestPart : requestParts) {
                StringBuilder content = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(requestPart.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append(System.lineSeparator());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(content);
            }

        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }


    private Map<String, String[]> decryptParamMap(HttpServletRequest request) {
        Map<String, String> urlParamMap = HttpServletRequestParseUtils.parseUrlParam(request);
        Map<String, String[]> resultMap = new HashMap<>();
        for (Map.Entry<String, String> entry : urlParamMap.entrySet()) {
            try {
                String key = entry.getKey();
                String decryptKey = RequestParamDecryptFilter.decrypt(key);
                String value = entry.getValue();
                String decryptValue;
                if (StrUtil.isBlank(value)) {
                    decryptValue = StrUtil.EMPTY;
                } else {
                    decryptValue = RequestParamDecryptFilter.decrypt(value);
                }
                resultMap.put(decryptKey, new String[]{decryptValue});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultMap;
    }

}
