package cn.edu.jxust.sort.util;

import java.util.UUID;

/**
 * @author: ddh
 * @data: 2020/1/3 14:42
 * @description UUID 工具类
 **/
public class UUIDUtil {
    /**
     * 获取 UUID
     *
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "");
    }
}
