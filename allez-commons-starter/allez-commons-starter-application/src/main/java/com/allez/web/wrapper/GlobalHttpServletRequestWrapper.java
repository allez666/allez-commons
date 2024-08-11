package com.allez.web.wrapper;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenyu
 * @date 2024/7/31 上午11:02
 * @description
 */
public class GlobalHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    private final Map<String, String[]> paramMap;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public GlobalHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            body = StreamUtils.copyToByteArray(request.getInputStream());
            paramMap = request.getParameterMap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        if (Objects.isNull(paramMap)) {
            return null;
        }
        return this.paramMap.get(name);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public String getBody() {
        return new String(body, StandardCharsets.UTF_8);
    }
}
