package cn.edu.jxust.sort.mqtt.handler;

/**
 * @author yuanweimin
 * @date 19/08/10 09:08
 * @description message arrive handler
 */
public interface MessageHandler {
    /**
     * message handle
     *
     * @param msgId   message id
     * @param msgBody message body
     */
    void handle(Long msgId, String msgBody);
}