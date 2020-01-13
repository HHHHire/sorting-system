package cn.edu.jxust.sort.service.impl;


import cn.edu.jxust.sort.entity.po.Inventory;
import cn.edu.jxust.sort.repository.InventoryRepository;
import cn.edu.jxust.sort.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Inventory getInventoryByCategoryId(String enterpriseId, String categoryId) {
        return inventoryRepository.findByEnterpriseIdAndCategoryId(enterpriseId, categoryId).orElse(null);
    }

    @Override
    public Inventory getInventoryByCategoryName(String enterpriseId, String categoryName) {
        return null;
    }

    @Override
    public Integer updateInventory(String enterpriseId, String categoryId, Integer counts){
        Integer count = inventoryRepository.findCounts(enterpriseId, categoryId);
        if (count != null) {
            return inventoryRepository.updateInventory(enterpriseId, categoryId, count + counts);
        } else {
            return null;
        }
    }
}
