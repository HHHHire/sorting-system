package cn.edu.jxust.sort.mqtt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author yuanweimin
 * @date 19/08/10 17:31
 * @description token 生成工具类
 */
@SuppressWarnings("ALL")
public class Token {
    static String assembleToken(String version,
                                String resourceName,
                                String expirationTime,
                                String signatureMethod,
                                String accessKey)
            throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String res = URLEncoder.encode(resourceName, "UTF-8");
        String sig = URLEncoder.encode(generatorSignature(version,
                resourceName,
                expirationTime,
                accessKey,
                signatureMethod), "UTF-8");
        sb.append("version=")
                .append(version)
                .append("&res=")
                .append(res)
                .append("&et=")
                .append(expirationTime)
                .append("&method=")
                .append(signatureMethod)
                .append("&sign=")
                .append(sig);
        return sb.toString();
    }

    static String generatorSignature(String version,
                                     String resourceName,
                                     String expirationTime,
                                     String accessKey,
                                     String signatureMethod)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String encryptText = expirationTime + "\n" + signatureMethod + "\n" + resourceName + "\n" + version;
        String signature;
        byte[] bytes = hmacEncrypt(encryptText, accessKey, signatureMethod);
        signature = Base64.getEncoder().encodeToString(bytes);
        return signature;
    }

    static byte[] hmacEncrypt(String data, String key, String signatureMethod)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signInKey = null;
        signInKey = new SecretKeySpec(Base64.getDecoder().decode(key),
                "Hmac" + signatureMethod.toUpperCase());
        Mac mac = null;
        mac = Mac.getInstance("Hmac" + signatureMethod.toUpperCase());
        mac.init(signInKey);
        return mac.doFinal(data.getBytes());
    }
}
