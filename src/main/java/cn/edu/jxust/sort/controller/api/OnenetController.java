package cn.edu.jxust.sort.controller.api;

import cn.edu.jxust.sort.util.OkHttpUtil;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.annotations.RequiredPermission;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ddh
 * @data: 2020/1/13 17:26
 * @description onenet api controller
 **/
@RestController
@RequestMapping("/api/onenet")
@PropertySource(value = "classpath:config/mq-config.properties")
public class OnenetController extends BaseController {
    private static final String RESPONSE_FLAG = "errno";
    private final Environment env;
    private final OkHttpUtil okHttpUtil;

    @Autowired
    public OnenetController(Environment env, OkHttpUtil okHttpUtil) {
        this.env = env;
        this.okHttpUtil = okHttpUtil;
    }

    @GetMapping("/{deviceId}")
    @RequiredPermission
    public Response getDeviceInfo(@PathVariable @NotBlank String deviceId) {
        Map<String, String> head = new HashMap<>(1);
        head.put("api-key", env.getProperty("apiKey"));
        JSONObject response = JSON.parseObject(okHttpUtil.get("http://api.heclouds.com/devices/" + deviceId, null, head));

        if (response != null) {
            if (response.getInteger(RESPONSE_FLAG) == 0) {
                Map<String, Object> status = new HashMap<>(1);
                status.put("online", response.getJSONObject("data").getBooleanValue("online"));
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, status);
            } else {
                return ResponseUtil.responseWithData(ResponseStatus.FAILED, "设备不存在");
            }
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.SYSTEM_ERROR);
        }
    }
}
