package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Binding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: ddh
 * @data: 2020/1/3 9:36
 * @description Binding 持久化
 **/
public interface BindingRepository extends JpaRepository<Binding, String> {

    /**
     * 通过设备 id 查询绑定信息
     *
     * @param deviceId 设备 id
     * @return Optional<Binding>
     */
    Optional<Binding> findByDeviceId(String deviceId);

    /**
     * 通过 mq 客户端 id 删除绑定信息
     *
     * @param clientId mq 客户端 id
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByClientId(String clientId);
}
