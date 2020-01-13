package cn.edu.jxust.sort.mqtt.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author yuanweimin
 * @date 19/09/10 08:18
 * @description
 */
@Slf4j
@Component
@Scope("prototype")
public class MessageArrivedHandler implements MessageHandler {

    @Override
    public void handle(Long msgId, String msgBody) {
        log.info("{}", msgBody);
//        Copper copper = parseData(msgBody);
//        // save copper to db
//        if (copper.getEnterpriseId() != null) {
//            log.info("saved copper: {}", copperRepository.save(copper));
//            logService.addLog(copper);
//        }
    }

    /*private Copper parseData(String content) {
        JSONObject object = JSON.parseObject(content);
        JSONObject app = object.getJSONObject("appProperty");

        String deviceId = app.getString("deviceId");
        Device device = deviceRepository.findById(deviceId).orElse(null);

        String body = object.getString("body");
        String[] arr = body.trim().split(",");

        return Copper.builder()
                .copperId(UUIDUtil.getUUID())
                .deviceId(deviceId)
                .deviceName((device != null) ? device.getDeviceName() : null)
                .enterpriseId((device != null) ? device.getEnterpriseId() : null)
                .theoreticalLength(Integer.valueOf(arr[0]))
                .actualLength(Integer.valueOf(arr[1]))
                .wireGage(BigDecimal.valueOf(Double.parseDouble(arr[2])))
                .totalRunTimeHour(Integer.valueOf(arr[3]))
                .totalRunTimeMin(Integer.valueOf(arr[4]))
                .electricEnergy(BigDecimal.valueOf(Long.parseLong(arr[5])))
                .faultMark(Integer.valueOf(arr[6]))
                .runFlag(Integer.valueOf(arr[7]))
                .wiringFailure(Integer.valueOf(arr[8]))
                .annealingFailure(Integer.valueOf(arr[9]))
                .createTime(Long.valueOf(app.getString("dataTimestamp")))
                .build();
    }*/
}
