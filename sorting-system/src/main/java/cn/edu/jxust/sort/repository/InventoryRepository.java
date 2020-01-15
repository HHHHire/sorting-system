package cn.edu.jxust.sort.repository;


import cn.edu.jxust.sort.entity.po.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author: ddh
 * @data: 2020/1/8 15:28
 * @description inventory 持久化
 **/
public interface InventoryRepository extends JpaRepository<Inventory, String> {

    /**
     * 分页查询库存信息
     *
     * @param enterpriseId 企业 id
     * @param pageable     分页信息
     * @return Page<Inventory>
     */
    Page<Inventory> findByEnterpriseId(String enterpriseId, Pageable pageable);

    /**
     * 通过分类编号查询库存
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @return Optional<Inventory>
     */
    List<Inventory> findByEnterpriseIdAndCategoryId(String enterpriseId, String categoryId);

    /**
     * 通过分类名称查询库存
     *
     * @param enterpriseId 企业 id
     * @param categoryName 分类名称
     * @return Optional<Inventory>
     */
    List<Inventory> findByEnterpriseIdAndCategoryName(String enterpriseId, String categoryName);

    @Query(value = "select * from ss_inventory si where si.enterprise_id=?1 and si.c_length=?2 and si.length_tolerance_po=?3 and si.length_tolerance_ne=?4 and si.weight=?5 and si.weight_tolerance=?6", nativeQuery = true)
    Optional<Inventory> findBylengthAndWeight(String enterpriseId, BigDecimal cLenght, BigDecimal lengthTolerancePo,
                                              BigDecimal lengthToleranceNe, BigDecimal weight, BigDecimal weightTolerance);

    /**
     * 更新库存
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @param count        数量
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update ss_inventory set " +
            "counts=:#{#count} " +
            "where enterprise_id=:#{#enterpriseId} " +
            "and category_id=:#{#categoryId}")
    Integer updateInventory(@Param("enterpriseId") String enterpriseId, @Param("categoryId") String categoryId, @Param("count") Integer count);

    /**
     * 更新库存
     *
     * @param enterpriseId 企业 id
     * @param inventoryId   分类编号
     * @param count        数量
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update ss_inventory set " +
            "counts=:#{#count} " +
            "where enterprise_id=:#{#enterpriseId} " +
            "and inventory_id=:#{#inventoryId}")
    Integer updateInventoryById(@Param("enterpriseId") String enterpriseId, @Param("inventoryId") String inventoryId, @Param("count") Integer count);

    /**
     * 查询库存数量
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @return Integer
     */
    @Query(value = "select si.counts from ss_inventory si where si.enterprise_id = ?1 and si.category_id = ?2", nativeQuery = true)
    Integer findCounts(String enterpriseId, String categoryId);
}
