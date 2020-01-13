package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
