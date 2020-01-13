package cn.edu.jxust.sort.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * @author: ddh
 * @data: 2020/1/12 21:12
 * @description
 **/
@Slf4j
@Component
public class OkHttpUtil {
    private final OkHttpClient client;

    @Autowired
    public OkHttpUtil(OkHttpClient client) {
        this.client = client;
    }

    /**
     * get 请求封装
     *
     * @param url    地址
     * @param params query 参数
     * @return String
     */
    public String get(String url, Map<String, String> params) {

        HttpUrl.Builder builder = requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                builder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        Request request = new Request.Builder().url(builder.build()).build();
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return requireNonNull(response.body()).string();
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
        return "";
    }

    /**
     * get 请求封装
     *
     * @param url   地址
     * @param query query 参数
     * @param head  head 参数
     * @return String
     */
    public String get(String url, Map<String, String> query, Map<String, String> head) {

        // 构建请求地址和 query params
        HttpUrl.Builder urlBuilder = requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (query != null) {
            for (Map.Entry<String, String> param : query.entrySet()) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        // 构建请求 headers
        Headers.Builder headBuilder = new Headers.Builder();
        if (head != null) {
            for (Map.Entry<String, String> param : head.entrySet()) {
                headBuilder.add(param.getKey(), param.getValue());
            }
        }

        // 封装请求
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .headers(headBuilder.build())
                .build();

        // 执行 get 请求
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return requireNonNull(response.body()).string();
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
        return "";
    }
}

