package cn.edu.jxust.sort.service.impl;


import cn.edu.jxust.sort.entity.po.Category;
import cn.edu.jxust.sort.entity.po.Inventory;
import cn.edu.jxust.sort.repository.CategoryRepository;
import cn.edu.jxust.sort.repository.InventoryRepository;
import cn.edu.jxust.sort.service.CategoryService;
import cn.edu.jxust.sort.util.UUIDUtil;
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
    private final InventoryRepository inventoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, InventoryRepository inventoryRepository) {
        this.categoryRepository = categoryRepository;
        this.inventoryRepository = inventoryRepository;
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

    @Override
    public Integer updateCategory(Category category) {
        Integer raw;
        // 查询是否有相同定义的
        Inventory inventory = inventoryRepository.findBylengthAndWeight(category.getEnterpriseId(), category.getCLength(), category.getLengthTolerancePo(), category.getLengthToleranceNe(),
                category.getWeight(), category.getWeightTolerance()).orElse(null);
        if (inventory == null) {
            raw = categoryRepository.updateCategory(category);
            // 如果没有，新建一条库存记录
            if (raw != 0) {
                inventoryRepository.save(Inventory.builder()
                        .inventoryId(UUIDUtil.getUUID())
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .cLength(category.getCLength())
                        .lengthToleranceNe(category.getLengthToleranceNe())
                        .lengthTolerancePo(category.getLengthTolerancePo())
                        .weight(category.getWeight())
                        .weightTolerance(category.getWeightTolerance())
                        .enterpriseId(category.getEnterpriseId())
                        .counts(0).build());
            }
        } else {
            raw = categoryRepository.updateCategory(category);
        }



        return raw;
    }

    @Override
    public Category createCategory(Category category) {
        // 同时创建库存
        inventoryRepository.save(Inventory.builder()
                .inventoryId(UUIDUtil.getUUID())
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .cLength(category.getCLength())
                .lengthToleranceNe(category.getLengthToleranceNe())
                .lengthTolerancePo(category.getLengthTolerancePo())
                .weight(category.getWeight())
                .weightTolerance(category.getWeightTolerance())
                .enterpriseId(category.getEnterpriseId())
                .counts(0).build());
        return categoryRepository.save(category);
    }

    @Override
    public Integer deleteCategory(String categoryId) {
        return categoryRepository.deleteByCategoryId(categoryId);
    }
}
