package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: ddh
 * @data: 2020/1/3 9:36
 * @description category 持久化
 **/
public interface CategoryRepository extends JpaRepository<Category, String> {

    /**
     * 分页查询分类信息
     *
     * @param enterpriseId 企业 id
     * @param pageable     分页信息
     * @return Page<Category>
     */
    Page<Category> findAllByEnterpriseId(String enterpriseId, Pageable pageable);

    /**
     * 通过设备 id 分页查询分类信息
     *
     * @param enterpriseId 企业 id
     * @param deviceId     设备 id
     * @param pageable     分页信息
     * @return Page<Category>
     */
    Page<Category> findByEnterpriseIdAndDeviceId(String enterpriseId, String deviceId, Pageable pageable);

    /**
     * 通过分类编号查询分类信息
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @return Category
     */
    Category findByEnterpriseIdAndCategoryId(String enterpriseId, String categoryId);

    /**
     * 更新分类
     *
     * @param category 分类实体
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update ss_category set " +
            "category_name=:#{#category.categoryName}, " +
            "category_length=:#{#category.categoryLength}, " +
            "length_tolerance_po=:#{#category.lengthTolerancePo}, " +
            "length_tolerance_ne=:#{#category.lengthToleranceNe}, " +
            "weight=:#{#category.weight}, " +
            "weight_tolerance=:#{#category.weightTolerance}, " +
            "enterprise_id=:#{#category.enterpriseId}, " +
            "device_id=:#{#category.deviceId}, " +
            "update_time=:#{#category.updateTime} " +
            "where category_id=:#{#category.categoryId}")
    Integer updateCategory(Category category);

    /**
     * 删除分类
     *
     * @param categoryId 分类编号
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByCategoryId(String categoryId);
}
