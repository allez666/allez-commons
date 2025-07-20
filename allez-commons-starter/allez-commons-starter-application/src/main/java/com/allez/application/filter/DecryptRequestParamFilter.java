//package com.allez.application.filter;
//
//import com.allez.application.config.FilterOrderConfig;
//import com.allez.application.constant.CommonConstant;
//import com.allez.application.utils.XORUtil;
//import com.allez.application.wrapper.HttpServletDecryptRequestParamWrapper;
//import com.allez.application.wrapper.HttpServletDecryptResponseParamWrapper;
//import org.springframework.boot.web.servlet.filter.OrderedFilter;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.util.FastByteArrayOutputStream;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.util.Objects;
//
///**
// * @author: chenyu
// * @create: 2024-12-16 21:55
// * @Description:
// */
//public class DecryptRequestParamFilter extends OncePerRequestFilter implements OrderedFilter {
//
//
//    @Override
//    public int getOrder() {
//        return FilterOrderConfig.REQUEST_PARAM_ENCRYPT_FILTER_ORDER;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String header = request.getHeader(CommonConstant.NO_DECRYPTION_HEADER_KEY);
//        boolean noDecrypt = Objects.equals(header, CommonConstant.NO_DECRYPTION_HEADER_VALUE);
//        if (request.getMethod().equals(HttpMethod.OPTIONS.name()) || noDecrypt) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        HttpServletDecryptRequestParamWrapper httpServletDecryptRequestParamWrapper = new HttpServletDecryptRequestParamWrapper(request);
//        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response) {
//            @Override
//            protected void copyBodyToResponse(boolean complete) throws IOException {
//                InputStream contentInputStream = getContentInputStream();
//                if (getContentSize() > 0) {
//                    HttpServletResponse rawResponse = (HttpServletResponse) getResponse();
////                    if ((complete || this.contentLength != null) && !rawResponse.isCommitted()) {
////                        if (rawResponse.getHeader(HttpHeaders.TRANSFER_ENCODING) == null) {
////                            rawResponse.setContentLength(complete ? getContentSize() : this.contentLength);
////                        }
////                        this.contentLength = null;
////                    }
////                    this.content.writeTo(rawResponse.getOutputStream());
////                    this.content.reset();
//                    byte[] bytes = contentInputStream.readAllBytes();
//                    String s = new String(bytes, CommonConstant.DEFAULT_CHARSETS);
//                    String s1 = XORUtil.encryptAndBase64(s, "709394");
//                    rawResponse.getOutputStream().write(s1.getBytes(StandardCharsets.UTF_8));
//                    if (complete) {
//                        super.flushBuffer();
//                    }
//                }
//            }
//        };
//        filterChain.doFilter(httpServletDecryptRequestParamWrapper, response);
//        contentCachingResponseWrapper.copyBodyToResponse();
//    }
//}
