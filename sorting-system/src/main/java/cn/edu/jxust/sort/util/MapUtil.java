package cn.edu.jxust.sort.util;

import java.util.HashMap;

/**
 * @author: ddh
 * @data: 2020/1/5 21:52
 * @description
 **/
@SuppressWarnings("unused")
public class MapUtil {
    private static final Integer STEP = 2;

    /**
     * 通过 strings 构造 Map
     *
     * @param strings 构造参数
     * @return HashMap
     */
    public static HashMap<String, String> create(String... strings) {
        HashMap<String, String> map = new HashMap<>(10);
        if ((strings.length & 1) == 1) {
            return map;
        } else {
            for (int i = 0; i < strings.length; i += STEP) {
                map.put(strings[i], strings[i + 1]);
            }
        }
        return map;
    }
}
