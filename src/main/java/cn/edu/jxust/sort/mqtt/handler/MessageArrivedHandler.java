package cn.edu.jxust.sort.mqtt.handler;

import cn.edu.jxust.sort.entity.po.Device;
import cn.edu.jxust.sort.repository.CategoryRepository;
import cn.edu.jxust.sort.repository.DeviceRepository;
import cn.edu.jxust.sort.repository.RecordRepository;
import cn.edu.jxust.sort.util.UUIDUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author yuanweimin
 * @date 19/09/10 08:18
 * @description
 */
@Slf4j
@Component
@Scope("prototype")
public class MessageArrivedHandler implements MessageHandler {

    private final DeviceRepository deviceRepository;
    private final RecordRepository recordRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public MessageArrivedHandler(DeviceRepository deviceRepository, RecordRepository recordRepository, CategoryRepository categoryRepository) {
        this.deviceRepository = deviceRepository;
        this.recordRepository = recordRepository;
        this.categoryRepository = categoryRepository;
    }

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

    /**
     * 将数据解析成 BusBarVO 实体
     *
     * @param content 数据字符串
     * @return BusBarVO
     */
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
