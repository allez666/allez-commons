package com.allez.application.wrapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.allez.application.utils.HttpServletRequestParseUtil;
import com.allez.application.utils.XORUtil;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author chenyu
 * @date 2024/8/20 15:54
 * @description 对请求参数进行解密，包含请求头解密、请求体、表单提交、文件流
 */
public class HttpServletDecryptRequestParamWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> headerMap;

    private final Map<String, String[]> paramMap;

    private final Collection<Part> parts;

    private static final String SECRET_KEY = "XKrCHvcwbCOvfJxwE7cjcs5ALnz9i0ElE05RlRJnT84=";

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public HttpServletDecryptRequestParamWrapper(HttpServletRequest request) {
        super(request);
        this.headerMap = decryptHeaders(request);
        this.paramMap = decryptParamMap(request);
        this.parts = decryptParts(request);
    }


    @Override
    public String getHeader(String name) {
        return this.headerMap.get(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return Collections.enumeration(CollUtil.toList(this.headerMap.get(name)));
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(this.headerMap.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.paramMap.get(name);
    }


    @Override
    public Map<String, String[]> getParameterMap() {
        return this.paramMap;
    }

    @Override
    public String getParameter(String name) {
        String[] strings = this.paramMap.get(name);
        return strings != null && strings.length > 0 ? strings[0] : null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.paramMap.keySet());
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        for (Part part : getParts()) {
            if (name.equals(part.getName())) {
                return part;
            }
        }
        return null;
    }

    @Override
    public Collection<Part> getParts() {
        return this.parts;
    }

    public Collection<Part> decryptParts(HttpServletRequest request) {
        List<Part> resultList = new ArrayList<>();
        try {
            Collection<Part> requestParts = request.getParts();
            for (Part requestPart : requestParts) {
                ApplicationPartDecryptWrapper decryptWrapper = new ApplicationPartDecryptWrapper(requestPart);
                resultList.add(decryptWrapper);
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }


    @SneakyThrows
    private Map<String, String[]> decryptParamMap(HttpServletRequest request) {
        Map<String, String[]> resultMap = new HashMap<>();

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            try {
                String key = entry.getKey();
                String decryptKey = XORUtil.decrypt(key, SECRET_KEY);
                String[] value = entry.getValue();
                String[] array = Arrays.stream(value).map(e -> {
                            if (StrUtil.isBlank(e)) {
                                return StrUtil.EMPTY;
                            } else {
                                return XORUtil.decrypt(e, SECRET_KEY);
                            }
                        })
                        .toArray(String[]::new);
                resultMap.put(decryptKey, array);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultMap;
    }

    private Map<String, String> decryptHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        Map<String, String> map = HttpServletRequestParseUtil.parseHeader(request);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            headerMap.put(XORUtil.decrypt(key, SECRET_KEY), XORUtil.decrypt(value, SECRET_KEY));
        }
        return headerMap;
    }


    public static class ApplicationPartDecryptWrapper implements Part {

        private final Part applicationPart;


        public ApplicationPartDecryptWrapper(Part applicationPart) throws IOException {
            this.applicationPart = applicationPart;
        }


        @Override
        public void delete() throws IOException {
            this.applicationPart.delete();
        }

        @Override
        public String getContentType() {
            return this.applicationPart.getContentType();
        }

        @Override
        public String getHeader(String name) {
            return this.applicationPart.getHeader(name);
        }

        @Override
        public Collection<String> getHeaderNames() {
            return this.applicationPart.getHeaderNames();
        }

        @Override
        public Collection<String> getHeaders(String name) {
            return this.applicationPart.getHeaders(name);
        }

        @Override
        public InputStream getInputStream() throws IOException {
            byte[] decrypt = XORUtil.decrypt(this.applicationPart.getInputStream(), SECRET_KEY);
            return new ByteArrayInputStream(decrypt);
        }

        @Override
        public String getName() {
            return XORUtil.decrypt(this.applicationPart.getName(), SECRET_KEY);
        }

        @Override
        public long getSize() {
            return this.applicationPart.getSize();
        }

        @Override
        public void write(String fileName) throws IOException {
            this.applicationPart.write(fileName);
        }

        @Override
        public String getSubmittedFileName() {
            return this.applicationPart.getSubmittedFileName();
        }
    }

}
