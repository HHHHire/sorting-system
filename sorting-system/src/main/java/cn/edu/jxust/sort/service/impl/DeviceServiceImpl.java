package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.Device;
import cn.edu.jxust.sort.repository.DeviceRepository;
import cn.edu.jxust.sort.service.DeviceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author: ddh
 * @data: 2020/1/4 20:10
 * @description
 **/
@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Integer deleteDeviceByEnterpriseId(String enterpriseId) {
        return deviceRepository.deleteByEnterpriseId(enterpriseId);
    }

    @Override
    public Page<Device> getAlldeviceByEnterpriseId(String enterpriseId, Pageable pageable) {
        return deviceRepository.findAllByEnterpriseId(enterpriseId, pageable);
    }

    @Override
    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Integer deleteDeviceByDeviceId(String deviceId) {
        return deviceRepository.deleteByDeviceId(deviceId);
    }
}
