package cn.edu.jxust.sort.mqtt;

import cn.edu.jxust.sort.config.MqttConfig;
import cn.edu.jxust.sort.mqtt.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @author yuanweimin
 * @date 19/08/11 09:14
 * @description mqtt client
 */
@Slf4j
public class MqClient {
    /**
     * mqtt client
     */
    private MqttClient client;
    /**
     * mqtt id
     */
    private String mqId;
    /**
     * client id
     */
    private String clientId;
    /**
     * sub topic url
     */
    private String subTopic;
    /**
     * 秘钥
     */
    private String accessKey;
    private Integer qos = 2;
    /**
     * message handler
     */
    private MessageHandler handler;

    /**
     * mqtt 客户端初始化
     *
     * @param mqId      mqtt id
     * @param topic     订阅主题
     * @param subscribe 订阅
     * @param clientId  客户端编号
     * @param handler   消息处理器
     */
    public MqClient(String mqId, String topic, String subscribe, String accessKey, String clientId, MessageHandler handler) {
        this.mqId = mqId;
        this.accessKey = accessKey;
        this.clientId = clientId;
        this.handler = handler;
        this.subTopic = String.format("$sys/pb/consume/%s/%s/%s", mqId, topic, subscribe);
    }

    /**
     * 开始连接onenet mqtt
     *
     * @return boolean
     */
    public boolean connect() {
        try {
            if (client == null) {
                client = new MqttClient(MqttConfig.SERVER_URL, clientId, new MemoryPersistence());
            }
            try {
                MqttConnectOptions options = setOptions();
                if (!client.isConnected()) {
                    client.connect(options);
                }
                client.setCallback(new SubCallback(this, this.handler));
                client.subscribe(subTopic, this.qos);
                log.info("[mqClient success]: {}", "sub success");
                return true;
            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException | CertificateException | KeyStoreException | KeyManagementException e) {
                log.error("[mqClient error]: {}", e.getMessage());
                return false;
            }
        } catch (MqttException e) {
            log.error("[mqClient error]: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 设置连接属性
     */
    private MqttConnectOptions setOptions() throws InvalidKeyException, NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        MqttConnectOptions options = new MqttConnectOptions();
        String userName = this.mqId;
        String passWord = Token.assembleToken(MqttConfig.VERSION, "mqs/" + userName, System.currentTimeMillis() / 1000 + 100 * 24 * 60 * 6 + "", "md5", this.accessKey);

        options.setCleanSession(true);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        options.setConnectionTimeout(20);
        options.setKeepAliveInterval(30);
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);

        InputStream stream = this.getClass().getResource("/config/onenet-mq.pem").openStream();
        options.setSocketFactory(SslUtil.getSocketFactory(stream));

        return options;
    }

    /**
     * 掉线重连
     */
    boolean reconnect() {
        if (client != null) {
            try {
                if (!client.isConnected()) {
                    client.connect(setOptions());
                }
                client.subscribe(subTopic, this.qos);
                log.info("reconnect success");
                return true;
            } catch (MqttException | InvalidKeyException | NoSuchAlgorithmException | IOException | CertificateException | KeyStoreException | KeyManagementException e) {
                log.error("[mqClient error]: {}", e.getMessage());
                return false;
            }
        } else {
            return connect();
        }
    }

}

