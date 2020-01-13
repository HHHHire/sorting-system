package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/8 16:00
 * @description inventory service
 **/
public interface InventoryService {

    /**
     * 分页获取库存信息
     *
     * @param enterpriseId 企业 id
     * @param pageable     分页信息
     * @return Page<Inventory>
     */
    Page<Inventory> getInventory(String enterpriseId, Pageable pageable);

    /**
     * 通过分类编号获取库存信息
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @return Inventory
     */
    Inventory getInventoryByCategoryId(String enterpriseId, String categoryId);

    /**
     * 更新库存
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @param counts       数量
     * @return Integer
     */
    Integer updateInventory(String enterpriseId, String categoryId, Integer counts);
}
