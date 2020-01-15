package cn.edu.jxust.sort.controller.api;


import cn.edu.jxust.sort.entity.po.Category;
import cn.edu.jxust.sort.entity.po.Record;
import cn.edu.jxust.sort.entity.vo.RecordVO;
import cn.edu.jxust.sort.service.CategoryService;
import cn.edu.jxust.sort.service.InventoryService;
import cn.edu.jxust.sort.service.RecordService;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.TokenUtil;
import cn.edu.jxust.sort.util.UUIDUtil;
import cn.edu.jxust.sort.util.common.Const;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/8 17:18
 * @description
 **/
@RestController
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;
    private final TokenUtil tokenUtil;
    private final CategoryService categoryService;
    private final InventoryService inventoryService;

    @Autowired
    public RecordController(RecordService recordService, TokenUtil tokenUtil, CategoryService categoryService, InventoryService inventoryService) {
        this.recordService = recordService;
        this.tokenUtil = tokenUtil;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
    }

    /**
     * 分页查询记录
     *
     * @param token      用户 token
     * @param categoryId 分类 id
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param page       页数
     * @param size       页面大小
     * @return Response
     */
    @GetMapping
    public Response getRecord(@RequestHeader("token") String token,
                              @RequestParam(value = "categoryId", required = false) String categoryId,
                              @RequestParam(value = "startTime", required = false) String startTime,
                              @RequestParam(value = "endTime", required = false) String endTime,
                              @RequestParam(value = "page", defaultValue = Const.DEFAULT_PAGE_NUMBER, required = false) Integer page,
                              @RequestParam(value = "size", defaultValue = Const.DEFAULT_PAGE_SIZE, required = false) Integer size) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Page<Record> records = recordService.getRecord(enterpriseId, categoryId, startTime, endTime, PageRequest.of(page, size));
        if (records != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS,
                    records.map(r -> RecordVO.builder()
                            .recordId(r.getRecordId())
                            .categoryId(r.getCategoryId())
                            .categoryName(r.getCategoryName())
                            .counts(r.getCounts())
                            .createTime(r.getCreateTime()).build()));
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }

    /**
     * 出库
     *
     * @param token        用户 token
     * @param categoryId   分类 id
     * @param counts       数量
     * @param employeeCard 员工卡号
     * @return Response
     */
    @PostMapping
    public Response setOutRecord(@RequestHeader("token") String token,
                                 @RequestParam("categoryId") String categoryId,
                                 @RequestParam("counts") String counts,
                                 @RequestParam("employeeCard") String employeeCard) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Category category = categoryService.getCategoryById(enterpriseId, categoryId);
        String recordId = UUIDUtil.getUUID();
        Integer outCounts = Integer.valueOf(counts);
        Record record = recordService.createRecord(Record.builder()
                .recordId(recordId)
                .categoryId(categoryId)
                .categoryName(category.getCategoryName())
                .counts(outCounts > 0 ? -outCounts : outCounts)
                .deviceId(category.getDeviceId())
                .enterpriseId(enterpriseId)
                .employeeCard(employeeCard)
                .build());
        if (record != null) {
            Integer i = inventoryService.updateInventory(enterpriseId, categoryId, outCounts > 0 ? -outCounts : outCounts);
            if (i != null && i > 0) {
                return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
            } else {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }
    }

    /**
     * 通过员工卡号查看出库记录
     *
     * @param token        用户 token
     * @param employeeCard 用户卡号
     * @return Response
     */
    @GetMapping("/out")
    public Response getOutRecordByCard(@RequestHeader("token") String token,
                                       @RequestParam("employeeCard") String employeeCard,
                                       @RequestParam("startTime") String startTime,
                                       @RequestParam("endTime") String endTime) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        List<Record> outRecord = recordService.getOutRecordByEmployeeCard(enterpriseId, employeeCard, startTime, endTime);
        if (outRecord != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, outRecord);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }

    /**
     * 查看出库记录
     *
     * @param token 用户 token
     * @return Response
     */
    @GetMapping("/out")
    public Response getOutRecord(@RequestHeader("token") String token) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        List<Record> outRecord = recordService.getOutRecord(enterpriseId);
        if (outRecord != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, outRecord);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }
}
