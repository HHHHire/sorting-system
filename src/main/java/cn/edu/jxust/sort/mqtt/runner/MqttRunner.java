package cn.edu.jxust.sort.mqtt.runner;

import cn.edu.jxust.sort.mqtt.MqClient;
import cn.edu.jxust.sort.mqtt.handler.MessageArrivedHandler;
import cn.edu.jxust.sort.repository.BindingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author yuanweimin
 * @date 19/08/16 12:26
 * @description mqtt 启动订阅
 */
@Component
public class MqttRunner implements CommandLineRunner {

    private final MessageArrivedHandler handler;
    private final BindingRepository bindingRepository;

    @Autowired
    public MqttRunner(MessageArrivedHandler handler, BindingRepository bindingRepository) {
        this.handler = handler;
        this.bindingRepository = bindingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 遍历绑定信息，创建客户端进行订阅
        bindingRepository.findAll()
                .forEach(e -> new MqClient(e.getMqId(), e.getTopic(), e.getSubscribe(), e.getAccessKey(), e.getClientId(), handler)
                        .connect());
    }
}

