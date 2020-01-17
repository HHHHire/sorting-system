package cn.edu.jxust.sort.controller.api;

import cn.edu.jxust.sort.entity.po.Plan;
import cn.edu.jxust.sort.entity.vo.PlanVO;
import cn.edu.jxust.sort.service.PlanService;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.TokenUtil;
import cn.edu.jxust.sort.util.UUIDUtil;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/16 20:31
 * @description
 **/
@RestController
@RequestMapping("/api/plan")
public class PlanController extends BaseController {
    private final PlanService planService;
    private final TokenUtil tokenUtil;

    public PlanController(PlanService planService, TokenUtil tokenUtil) {
        this.planService = planService;
        this.tokenUtil = tokenUtil;
    }

    /**
     * 创建分拣规划
     *
     * @param token  用户 token
     * @param planVO 规划实体
     * @return Response
     */
    @PostMapping
    public Response createPlan(@RequestHeader("token") String token,
                               @RequestBody PlanVO planVO) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Plan plan = planService.createPlan(Plan.builder()
                .planId(UUIDUtil.getUUID())
                .categoryId(planVO.getCategoryId())
                .sortPortId(planVO.getSortPortId())
                .planCounts(planVO.getPlanCounts())
                .completeCounts(0)
                .enterpriseId(enterpriseId).build());
        if (plan == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
        }
    }

    /**
     * 获取分拣规划
     *
     * @param token 用户 token
     * @return Response
     */
    @GetMapping
    public Response getPlan(@RequestHeader("token") String token) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        List<Plan> plans = planService.getPlan(enterpriseId);
        if (plans == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        } else {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, plans);
        }
    }

    /**
     * 通过分拣口编号获取分拣规划
     *
     * @param token      用户 token
     * @param sortPortId 分拣口编号
     * @return Response
     */
    @GetMapping("/{sortPortId}")
    public Response getPlanBySortPortId(@RequestHeader("token") String token,
                                        @PathVariable String sortPortId) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Plan plan = planService.getPlanBySortPortId(enterpriseId, sortPortId);
        if (plan != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, plan);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }
    }

    /**
     * 修改分拣规划
     *
     * @param token  用户 token
     * @param planVO 分拣规划实体
     * @return Response
     */
    @PutMapping
    public Response updatePlan(@RequestHeader("token") String token,
                               @RequestBody PlanVO planVO) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Integer plan = planService.updatePlan(Plan.builder()
                .categoryId(planVO.getCategoryId())
                .sortPortId(planVO.getSortPortId())
                .planCounts(planVO.getPlanCounts())
                .completeCounts(0)
                .enterpriseId(enterpriseId)
                .updateTime(System.currentTimeMillis()).build());
        if (plan > 0) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, plan);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }
    }
}
