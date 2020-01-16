package cn.edu.jxust.sort.service.impl;


import cn.edu.jxust.sort.entity.po.Inventory;
import cn.edu.jxust.sort.repository.InventoryRepository;
import cn.edu.jxust.sort.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author: ddh
 * @data: 2020/1/8 16:00
 * @description
 **/
@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Page<Inventory> getInventory(String enterpriseId, Pageable pageable) {
        return inventoryRepository.findByEnterpriseId(enterpriseId, pageable);
    }

    @Override
    public List<Inventory> getInventoryByCategoryId(String enterpriseId, String categoryId) {
        return inventoryRepository.findByEnterpriseIdAndCategoryId(enterpriseId, categoryId);
    }

    @Override
    public List<Inventory> getInventoryByCategoryName(String enterpriseId, String categoryName) {
        return inventoryRepository.findByEnterpriseIdAndCategoryName(enterpriseId, categoryName);
    }

    @Override
    public Integer updateInventory(String enterpriseId, String categoryId, String cLenght, String lengthTolerancePo,
                                   String lengthToleranceNe, String weight, String weightTolerance, Integer counts) {
        List<Inventory> inventories = inventoryRepository.findByEnterpriseIdAndCategoryId(enterpriseId, categoryId);
        if (inventories.size() > 1) {
            // 旧的类别还有货 更新旧的类别的库存
            Inventory inventory = inventoryRepository.findBylengthAndWeight(enterpriseId, new BigDecimal(cLenght), new BigDecimal(lengthTolerancePo), new BigDecimal(lengthToleranceNe), new BigDecimal(weight), new BigDecimal(weightTolerance)).orElse(null);
            if (inventory != null) {
                return inventoryRepository.updateInventoryById(enterpriseId, inventory.getInventoryId(), counts);
            }
        } else {
            Integer count = inventoryRepository.findCounts(enterpriseId, categoryId);
            if (count != null) {
                return inventoryRepository.updateInventory(enterpriseId, categoryId, count + counts);
            }
        }
        return null;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
}
