package com.allez.application.utils;

import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author chenyu
 * @date 2024/8/20 17:44
 * @description xor加密， 原理：a^b^b == a
 */
public class XORUtil {

    private static final String SECRET_KEY = "XKrCHvcwbCOvfJxwE7cjcs5ALnz9i0ElE05RlRJnT84=";

    private static final Charset CHARSETS = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        String aaa = "aaa";
        String bbb = "bbb";
        String ccc = "ccc";

        byte[] decrypt = decrypt(aaa.getBytes(CHARSETS), SECRET_KEY.getBytes(CHARSETS));

        System.out.println(new String(decrypt, CHARSETS));

        byte[] decrypt1 = decrypt(decrypt, SECRET_KEY.getBytes(CHARSETS));
        System.out.println(new String(decrypt1, CHARSETS));

    }

    public static String decrypt(String data, String key) {
        if (StrUtil.isBlank(data) || StrUtil.isBlank(key)) {
            return data;
        }
        byte[] decrypt = decrypt( Base64.getDecoder().decode(data), key.getBytes(CHARSETS));
        return new String(decrypt, CHARSETS);
    }

    /**
     * 异或算法加密/解密
     *
     * @param data 数据（密文/明文）
     * @param key  密钥
     * @return 返回解密/加密后的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return data;
        }

        byte[] result = new byte[data.length];

        // 使用密钥字节数组循环加密或解密
        for (int i = 0; i < data.length; i++) {
            // 数据与密钥异或, 再与循环变量的低8位异或（增加复杂度）
            result[i] = (byte) (data[i] ^ key[i % key.length] ^ (i & 0xFF));
        }

        return result;
    }

    public static byte[] decrypt(InputStream in, String key) {
        byte[] bytes = key.getBytes(CHARSETS);
        return decrypt(in, bytes);
    }


    public static byte[] decrypt(InputStream in, byte[] key) {
        try {
            int b;
            int i = 0;
            byte[] resultBytes = new byte[in.available()];
            while ((b = in.read()) != -1) {
                resultBytes[i] = (byte) b;
                // 循环变量递增
                i++;
            }
            return decrypt(resultBytes, key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(in);
        }
    }

    /**
     * 对文件异或算法加密/解密
     *
     * @param inFile  输入文件（密文/明文）
     * @param outFile 结果输出文件
     * @param key     密钥
     */
    public static void decryptFile(File inFile, File outFile, byte[] key) throws Exception {
        InputStream in = null;
        OutputStream out = null;

        try {
            // 文件输入流
            in = new FileInputStream(inFile);
            // 结果输出流, 异或运算时, 字节是一个一个读取和写入, 这里必须使用缓冲流包装,
            // 等缓冲到一定数量的字节（10240字节）后再写入磁盘（否则写磁盘次数太多, 速度会非常慢）
            out = new BufferedOutputStream(new FileOutputStream(outFile), 10240);

            int b = -1;
            long i = 0;

            // 每次循环读取文件的一个字节, 使用密钥字节数组循环加密或解密
            while ((b = in.read()) != -1) {
                // 数据与密钥异或, 再与循环变量的低8位异或（增加复杂度）
                b = (b ^ key[(int) (i % key.length)] ^ (int) (i & 0xFF));
                // 写入一个加密/解密后的字节
                out.write(b);
                // 循环变量递增
                i++;
            }
            out.flush();

        } finally {
            close(in);
            close(out);
        }
    }

    private static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                // nothing
            }
        }
    }

}
