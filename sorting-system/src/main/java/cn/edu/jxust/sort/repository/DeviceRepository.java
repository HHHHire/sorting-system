package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: ddh
 * @data: 2020/1/3 9:36
 * @description device 持久化
 **/
public interface DeviceRepository extends JpaRepository<Device, String> {

    /**
     * 通过企业 id 删除设备信息
     *
     * @param enterpriseId 企业 id
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByEnterpriseId(String enterpriseId);

    /**
     * 分页查询所有设备信息
     *
     * @param enterpriseId 企业 id
     * @param pageable     分页信息
     * @return Page<Device>
     */
    Page<Device> findAllByEnterpriseId(String enterpriseId, Pageable pageable);

    /**
     * 通过设备 id 删除设备信息
     *
     * @param deviceId 设备 id
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByDeviceId(String deviceId);
}
