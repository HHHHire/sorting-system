package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Binding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author: ddh
 * @data: 2020/1/12 21:06
 * @description binding service
 **/
public interface BindingService {

    /**
     * 判断是否已经绑定到云平台
     *
     * @param deviceId 设备 id
     * @return Boolean
     */
    Boolean isExist(String deviceId);

    /**
     * 创建绑定
     *
     * @param binding 绑定实体
     * @return Binding
     */
    Binding createBind(Binding binding);

    /**
     * 查询设备是否存在于数据库
     *
     * @param deviceId 设备 id
     * @return Boolean
     */
    Boolean isExistInDb(String deviceId);

    /**
     * 分页获取绑定信息
     *
     * @param pageable 分页信息
     * @return Page<Binding>
     */
    Page<Binding> getBoundByPaging(Pageable pageable);

    /**
     * 通过 mq 客户端 id 删除绑定信息
     *
     * @param clientId mq 客户端 id
     * @return Boolean
     */
    Boolean deleteBoundByClientId(String clientId);
}
