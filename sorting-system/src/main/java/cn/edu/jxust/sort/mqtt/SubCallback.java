package cn.edu.jxust.sort.mqtt;

import cn.edu.jxust.sort.mqtt.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author yuanweimin
 * @date 19/08/10 08:54
 * @description mq subscribe callback
 */
@Slf4j
public class SubCallback implements MqttCallback {

    private MqClient client;
    private MessageHandler handler;
    private int reConnTimes = 0;

    SubCallback(MqClient client, MessageHandler handler) {
        this.client = client;
        this.handler = handler;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.error("lost connect");
        while (!client.reconnect()) {
            try {
                //超过20次后每10s重连一次
                if (reConnTimes++ > 20) {
                    Thread.sleep(10000);
                } else {
                    //前20次每秒重连一次
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log.error("{}", e.getMessage());
            }
        }
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        // 数据 protobuf 解码
        OnenetMq.Msg msg = OnenetMq.Msg.parseFrom(mqttMessage.getPayload());
        handler.handle(msg.getMsgid(), new String(msg.getData().toByteArray()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("delivery complete");
    }
}

