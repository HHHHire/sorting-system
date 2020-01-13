package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.Binding;
import cn.edu.jxust.sort.repository.BindingRepository;
import cn.edu.jxust.sort.service.BindingService;
import cn.edu.jxust.sort.util.OkHttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ddh
 * @data: 2020/1/12 21:09
 * @description
 **/
@Service
@PropertySource(value = "classpath:config/mq-config.properties")
public class BindingServiceImpl implements BindingService {

    private static final String RESPONSE_FLAG = "errno";

    private final BindingRepository bindingRepository;
    private final OkHttpUtil okHttpUtil;
    private final Environment env;

    @Autowired
    public BindingServiceImpl(BindingRepository bindingRepository, OkHttpUtil okHttpUtil, Environment env) {
        this.bindingRepository = bindingRepository;
        this.okHttpUtil = okHttpUtil;
        this.env = env;
    }

    @Override
    public Boolean isExist(String deviceId) {
        Map<String, String> head = new HashMap<>();
        head.put("api-key", env.getProperty("apiKey"));
        JSONObject response = JSON.parseObject(okHttpUtil.get("http://api.heclouds.com/devices/" + deviceId, null, head));
        if (response != null){
            return response.getInteger(RESPONSE_FLAG) == 0;
        }
        return false;
    }

    @Override
    public Binding createBind(Binding binding) {
        return bindingRepository.save(binding);
    }

    @Override
    public Boolean isExistInDb(String deviceId) {
        return bindingRepository.findByDeviceId(deviceId).isPresent();
    }

    @Override
    public Page<Binding> getBoundByPaging(Pageable pageable) {
        return bindingRepository.findAll(pageable);
    }

    @Override
    public Boolean deleteBoundByClientId(String clientId) {
        Integer row = bindingRepository.deleteByClientId(clientId);
        if (row == 0) {
            return false;
        }
        return true;
    }
}
