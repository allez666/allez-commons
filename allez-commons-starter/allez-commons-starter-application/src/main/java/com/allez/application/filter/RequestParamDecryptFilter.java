package com.allez.application.filter;

import com.alibaba.fastjson.JSON;
import com.allez.application.GlobalRequestContextHolder;
import com.allez.application.entity.RequestDetailInfo;
import com.allez.application.util.HttpServletRequestParseUtils;
import com.allez.application.wrapper.HttpServletDecryptRequestParamWrapper;
import com.allez.lang.enums.BoooleanEnum;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

/**
 * @author chenyu
 * @date 2024/8/20 10:38
 * @description
 */
public class RequestParamDecryptFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        RequestDetailInfo requestDetailInfo = GlobalRequestContextHolder.getRequestDetailInfo();

        // 是否要解密参数
        boolean decryptRequestParam = requestDetailInfo.getHeaderParam().getDecryptRequestParam();
        if (BoooleanEnum.FALSE.equalsByBoolValue(decryptRequestParam)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletDecryptRequestParamWrapper requestParamDecryptFilter = new HttpServletDecryptRequestParamWrapper(httpRequest);
        Map<String, Object> stringObjectMap = HttpServletRequestParseUtils.parseFormData(requestParamDecryptFilter);
        System.out.println(JSON.toJSONString(request.getParameterMap()));
        chain.doFilter(request, response);
    }

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private static String SECRET_KEY = "XKrCHvcwbCOvfJxwE7cjcs5ALnz9i0ElE05RlRJnT84=";

    public static String encrypt(String data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // 解密
    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encrypt("ccc"));
        System.out.println(encrypt("333"));
        System.out.println(encrypt("ddd"));
        System.out.println(encrypt("222"));
    }

}
