package cn.edu.jxust.sort.controller.api;


import cn.edu.jxust.sort.entity.po.Record;
import cn.edu.jxust.sort.entity.vo.RecordVO;
import cn.edu.jxust.sort.service.CategoryService;
import cn.edu.jxust.sort.service.InventoryService;
import cn.edu.jxust.sort.service.RecordService;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.TokenUtil;
import cn.edu.jxust.sort.util.UUIDUtil;
import cn.edu.jxust.sort.util.annotations.RequiredPermission;
import cn.edu.jxust.sort.util.common.Const;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Autowired
    public RecordController(RecordService recordService, TokenUtil tokenUtil) {
        this.recordService = recordService;
        this.tokenUtil = tokenUtil;
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
    @RequiredPermission
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
    @RequiredPermission
    @PostMapping("/out")
    public Response setOutRecord(@RequestHeader("token") String token,
                                 @RequestParam("categoryId") String categoryId,
                                 @RequestParam("categoryName") String categoryName,
                                 @RequestParam("counts") String counts,
                                 @RequestParam("categoryLength") String categoryLength,
                                 @RequestParam("lengthTolerancePo") String lengthTolerancePo,
                                 @RequestParam("lengthToleranceNe") String lengthToleranceNe,
                                 @RequestParam("weight") String weight,
                                 @RequestParam("weightTolerance") String weightTolerance,
                                 @RequestParam("employeeCard") String employeeCard) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Integer outCounts = Integer.valueOf(counts);
        Integer record = recordService.createRecord(Record.builder()
                .recordId(UUIDUtil.getUUID())
                .categoryId(categoryId)
                .categoryName(categoryName)
                .counts(outCounts > 0 ? -outCounts : outCounts)
                .categoryLength(new BigDecimal(categoryLength))
                .lengthTolerancePo(new BigDecimal(lengthTolerancePo))
                .lengthToleranceNe(new BigDecimal(lengthToleranceNe))
                .weight(new BigDecimal(weight))
                .weightTolerance(new BigDecimal(weightTolerance))
                .enterpriseId(enterpriseId)
                .employeeCard(employeeCard)
                .build());
        if (record != 0) {
            return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }
    }

    /**
     * 查看出库记录
     *
     * @param token 用户 token
     * @return Response
     */
    @RequiredPermission
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

    /**
     * 通过员工卡号查看出库记录
     *
     * @param token        用户 token
     * @param employeeCard 用户卡号
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return Response
     */
    @RequiredPermission
    @GetMapping("/outByCard")
    public Response getOutRecordByCard(@RequestHeader("token") String token,
                                       @RequestParam("employeeCard") String employeeCard,
                                       @RequestParam(value = "startTime", required = false) String startTime,
                                       @RequestParam(value = "endTime", required = false) String endTime) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        List<Record> outRecord = recordService.getOutRecordByEmployeeCard(enterpriseId, employeeCard, startTime, endTime);
        if (outRecord != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, outRecord);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }

    /**
     * 通过员工卡号查看入库记录
     *
     * @param token        用户 token
     * @param employeeCard 用户卡号
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return Response
     */
    @RequiredPermission
    @GetMapping("/inByCard")
    public Response getInRecordByCard(@RequestHeader("token") String token,
                                      @RequestParam("employeeCard") String employeeCard,
                                      @RequestParam(value = "startTime", required = false) String startTime,
                                      @RequestParam(value = "endTime", required = false) String endTime) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        List<Record> inRecord = recordService.getInRecordByEmployeeCard(enterpriseId, employeeCard, startTime, endTime);
        if (inRecord != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, inRecord);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }

    /**
     * 获取当天产量
     *
     * @param token 用户 token
     * @return Response
     */
    @RequiredPermission
    @GetMapping("/output/today")
    public Response getOutputToday(@RequestHeader("token") String token) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, recordService.getOutputToday(enterpriseId));
    }

    /**
     * 获取一周产量
     *
     * @param token 用户 token
     * @return Response
     */
    @RequiredPermission
    @GetMapping("/output/week")
    public Response getOutputWeek(@RequestHeader("token") String token) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        List<Integer> outputWeek = recordService.getOutputWeek(enterpriseId);
        if (outputWeek == null || outputWeek.size() < 7) {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        } else {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, outputWeek);
        }
    }

    /**
     * 获取一年的产量
     *
     * @param token 用户 token
     * @return Response
     */
    @RequiredPermission
    @GetMapping("/output/year")
    public Response getOutputYear(@RequestHeader("token") String token) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        List<Integer> outputYear = recordService.getOutputYear(enterpriseId);
        if (outputYear == null || outputYear.size() < 12) {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        } else {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, outputYear);
        }
    }

    /**
     * 获取员工今日工作量
     *
     * @param token        用户 token
     * @param employeeCard 员工卡号
     * @return Response
     */
    @RequiredPermission
    @GetMapping("/workload/today")
    public Response getWorkloadToday(@RequestHeader("token") String token,
                                     @RequestParam("employeeCard") String employeeCard) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, recordService.getWorkloadToday(enterpriseId, employeeCard));
    }

    /**
     * 获取员工这周工作量
     *
     * @param token        用户 token
     * @param employeeCard 员工卡号
     * @return Response
     */
    @RequiredPermission
    @GetMapping("/workload/week")
    public Response getWorkloadWeek(@RequestHeader("token") String token,
                                    @RequestParam("employeeCard") String employeeCard) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        List<Integer> workloadWeek = recordService.getWorkloadWeek(enterpriseId, employeeCard);
        if (workloadWeek != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, workloadWeek);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }
    }

    /**
     * 获取员工这周工作效率
     *
     * @param token        用户 token
     * @param employeeCard 员工卡号
     * @return Response
     */
    @RequiredPermission
    @GetMapping("/workEff/week")
    public Response getWorkEffWeek(@RequestHeader("token") String token,
                                   @RequestParam("employeeCard") String employeeCard) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        List<String> workEffWeek = recordService.getWorkEffWeek(enterpriseId, employeeCard);
        if (workEffWeek != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, workEffWeek);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }
    }

    /**
     * 添加记录(测试用)
     *
     * @param token  用户 token
     * @param record 记录实体
     * @return Response
     */
    @RequiredPermission
    @PostMapping("/in")
    public Response createRecord(@RequestHeader("token") String token,
                                 @RequestBody Record record) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Integer record1 = recordService.createRecord(Record.builder()
                .recordId(UUIDUtil.getUUID())
                .categoryId(record.getCategoryId())
                .categoryName(record.getCategoryName())
                .counts(record.getCounts())
                .categoryLength(record.getCategoryLength())
                .lengthTolerancePo(record.getLengthTolerancePo())
                .lengthToleranceNe(record.getLengthToleranceNe())
                .weight(record.getWeight())
                .weightTolerance(record.getWeightTolerance())
                .sortPortId(record.getSortPortId())
                .enterpriseId(enterpriseId)
                .employeeCard(record.getEmployeeCard()).build());
        if (record1 == 0) {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
        }
    }

}
