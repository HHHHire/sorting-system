package cn.edu.jxust.sort.repository;


import cn.edu.jxust.sort.entity.po.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
    Optional<Inventory> findByEnterpriseIdAndCategoryId(String enterpriseId, String categoryId);

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
     * 查询库存数量
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @return Integer
     */
    @Query(value = "select si.counts from ss_inventory si where si.enterprise_id = ?1 and si.category_id = ?2", nativeQuery = true)
    Integer findCounts(String enterpriseId, String categoryId);
}
