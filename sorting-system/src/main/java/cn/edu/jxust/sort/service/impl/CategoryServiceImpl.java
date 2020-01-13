package cn.edu.jxust.sort.service.impl;


import cn.edu.jxust.sort.entity.po.Category;
import cn.edu.jxust.sort.repository.CategoryRepository;
import cn.edu.jxust.sort.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author: ddh
 * @data: 2020/1/8 16:01
 * @description
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Category> getCategory(String enterpriseId, String deviceId, Pageable pageable) {
        if (deviceId == null) {
            return categoryRepository.findAllByEnterpriseId(enterpriseId, pageable);
        } else {
            return categoryRepository.findByEnterpriseIdAndDeviceId(enterpriseId, deviceId, pageable);
        }
    }

    @Override
    public Category getCategoryById(String enterpriseId, String categoryId) {
        return categoryRepository.findByEnterpriseIdAndCategoryId(enterpriseId, categoryId);
    }
}
