package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author: ddh
 * @data: 2020/1/8 15:59
 * @description category service
 **/
public interface CategoryService {

    /**
     * 分页获取分类信息
     *
     * @param enterpriseId 企业 id
     * @param deviceId     设备 id
     * @param pageable     分页信息
     * @return Page<Category>
     */
    Page<Category> getCategory(String enterpriseId, String deviceId, Pageable pageable);

    /**
     * 通过分类编号获取指定分类信息
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @return Category
     */
    Category getCategoryById(String enterpriseId, String categoryId);

    /**
     * 修改分类
     *
     * @param category 分类实体
     * @return Integer
     */
    Integer updateCategory(Category category);

    /**
     * 创建分类
     *
     * @param category 分类实体
     * @return Category
     */
    Category createCategory(Category category);

    /**
     * 通过分类编号删除分类信息
     *
     * @param categoryId 分类编号
     * @return Integer
     */
    Integer deleteCategory(String categoryId);
}
