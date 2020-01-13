package cn.edu.jxust.sort.util;

import org.springframework.util.DigestUtils;

/**
 * @author: ddh
 * @data: 2020/1/3 11:33
 * @description
 **/
public class EncryptionUtil {
    /**
     * 对字符串进行md5加密
     *
     * @param source 原字符串
     * @return 加密后字符串
     */
    public static String encrypt(String source) {
        return DigestUtils
                .md5DigestAsHex(source.getBytes());
    }
}
