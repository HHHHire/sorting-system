package cn.edu.jxust.sort.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * @author: ddh
 * @data: 2020/1/14 18:17
 * @description
 **/
@Component
public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    static {
        String fileName = "config/redis-config.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(Objects.requireNonNull(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)), StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error("配置文件读取异常", e);
        }
    }

    public static String getProperties(String key) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperties(String key, String defaultValue) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isEmpty(value)) {
            value = defaultValue;
        }
        return value.trim();
    }
}
