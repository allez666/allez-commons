package com.allez.application.wrapper;

import com.allez.application.entity.RequestDetailInfo;
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
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

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
            // getParameterMap 要在wrap流之前 ，不然如果是表单提交会丢失表单参数
            // 原因：getParameterMap时 会去解析参数 parseParameters，里面会去解析表单参数，如果流已经读取，那就不解析表单参数。
            // 源码：Request.java:3167
            paramMap = request.getParameterMap();
            body = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getParameter(String name) {
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.paramMap;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.paramMap.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
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
