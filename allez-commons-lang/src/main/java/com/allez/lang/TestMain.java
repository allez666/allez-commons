package com.allez.lang;

import cn.hutool.core.util.StrUtil;

/**
 * @author chenyu
 * @date 2024/3/8 18:39
 * @description
 */
public class TestMain {


    public static void main(String[] args) {

        String c = "        c";
        boolean b = StrUtil.isNotEmpty(c);
        System.out.println(b);


    }
}
