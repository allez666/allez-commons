package com.allez.application.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author chenyu
 * @date 2024/12/18 13:53
 * @description
 */
public interface CommonConstant {

    String NO_DECRYPTION_HEADER_KEY = "aaa";

    String NO_DECRYPTION_HEADER_VALUE = "ccc";


    /**
     * 版本号分隔符
     */
    String VERSION_SEPARATOR = ".";

    Charset DEFAULT_CHARSETS = StandardCharsets.UTF_8;
}
