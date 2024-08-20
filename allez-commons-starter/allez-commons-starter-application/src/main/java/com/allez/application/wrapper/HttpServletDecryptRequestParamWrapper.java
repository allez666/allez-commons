package com.allez.application.wrapper;

import com.allez.application.filter.RequestParamDecryptFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        try {
            this.headerMap = new HashMap<>();
            this.paramMap = decryptParamMap(request);
            this.parts = request.getParts();
            this.urlQueryString = request.getQueryString();
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
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


    private Map<String, String[]> decryptParamMap(HttpServletRequest request) {
        Map<String, String[]> resultMap = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();

            try {
                String decryptKey = RequestParamDecryptFilter.decrypt(key);
                if (Objects.nonNull(value)) {
                    String[] decryptValue = new String[value.length];
                    for (int i = 0; i < value.length; i++) {
                        decryptValue[i] = RequestParamDecryptFilter.decrypt(URLDecoder.decode(value[i], StandardCharsets.UTF_8));
                    }
                    resultMap.put(decryptKey, decryptValue);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        return resultMap;
    }

}
