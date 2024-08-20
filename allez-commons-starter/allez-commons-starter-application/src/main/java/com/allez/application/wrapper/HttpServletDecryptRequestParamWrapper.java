package com.allez.application.wrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author chenyu
 * @date 2024/8/20 15:54
 * @description 对请求参数进行解密
 */
public class HttpServletDecryptRequestParamWrapper extends GlobalHttpServletRequestWrapper {

    private final Map<String,String> headerMap;

    private final Map<String,String[]> paramMap;

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


}
