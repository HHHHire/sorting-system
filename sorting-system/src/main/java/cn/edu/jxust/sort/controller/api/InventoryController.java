package cn.edu.jxust.sort.controller.api;


import cn.edu.jxust.sort.entity.po.Inventory;
import cn.edu.jxust.sort.entity.vo.InventoryVO;
import cn.edu.jxust.sort.service.InventoryService;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.TokenUtil;
import cn.edu.jxust.sort.util.common.Const;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/8 17:17
 * @description
 **/
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final TokenUtil tokenUtil;
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(TokenUtil tokenUtil, InventoryService inventoryService) {
        this.tokenUtil = tokenUtil;
        this.inventoryService = inventoryService;
    }

    /**
     * 分页查询库存
     *
     * @param token 用户 token
     * @param page  页数
     * @param size  页面大小
     * @return Response
     */
    @GetMapping
    public Response getInventory(@RequestHeader("token") String token,
                                 @RequestParam(value = "page", defaultValue = Const.DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                 @RequestParam(value = "size", defaultValue = Const.DEFAULT_PAGE_SIZE, required = false) Integer size) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Page<Inventory> inventories = inventoryService.getInventory(enterpriseId, PageRequest.of(page, size));
        if (inventories != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS,
                    inventories.map(i -> InventoryVO.builder()
                            .categoryName(i.getCategoryName())
                            .counts(i.getCounts())
                            .updateTime(i.getUpdateTime()).build()));
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }

    /**
     * 通过类别查询库存
     *
     * @param token      用户 token
     * @param categoryId 分类 id
     * @return Response
     */
    @GetMapping("/{categoryId}")
    public Response getInventoryByCategoryId(@RequestHeader("token") String token,
                                             @PathVariable String categoryId) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Inventory inventory = inventoryService.getInventoryByCategoryId(enterpriseId, categoryId);
        if (inventory != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, InventoryVO.builder()
                    .categoryName(inventory.getCategoryName())
                    .counts(inventory.getCounts())
                    .updateTime(inventory.getUpdateTime()).build());
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }
}
