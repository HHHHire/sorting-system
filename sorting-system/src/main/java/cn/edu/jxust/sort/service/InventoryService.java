package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
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
    Page<Inventory> getInventory(String enterpriseId, String categoryId, Pageable pageable);

    /**
     * 通过分类名称获取库存信息
     *
     * @param enterpriseId 企业 id
     * @param categoryName 分类名称
     * @return Inventory
     */
    List<Inventory> getInventoryByCategoryName(String enterpriseId, String categoryName);

    /**
     * 更新库存
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @param counts       数量
     * @return Integer
     */
    Integer updateInventory(String enterpriseId, String categoryId, String cLenght, String lengthTolerancePo,
                            String lengthToleranceNe, String weight, String weightTolerance, Integer counts);

    /**
     * 创建库存
     *
     * @param inventory 库存实体
     * @return Inventory
     */
    Inventory createInventory(Inventory inventory);
}
