package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author: ddh
 * @data: 2020/1/4 20:09
 * @description device service
 **/
public interface DeviceService {

    /**
     * 通过企业 id 删除设备
     *
     * @param enterpriseId 企业 id
     * @return Integer
     */
    Integer deleteDeviceByEnterpriseId(String enterpriseId);

    /**
     * 分页获取企业所有设备
     *
     * @param enterpriseId 企业 id
     * @param pageable     分页信息
     * @return Page<Device>
     */
    Page<Device> getAlldeviceByEnterpriseId(String enterpriseId, Pageable pageable);

    /**
     * 创建设备
     *
     * @param device 设备实体
     * @return Device
     */
    Device createDevice(Device device);

    /**
     * 通过设备 id 删除设备
     *
     * @param deviceId 设备 id
     * @return Integer
     */
    Integer deleteDeviceByDeviceId(String deviceId);
}
