package cn.edu.jxust.sort.controller.api;

import cn.edu.jxust.sort.entity.po.Employee;
import cn.edu.jxust.sort.entity.vo.EmployeeVO;
import cn.edu.jxust.sort.service.EmployeeService;
import cn.edu.jxust.sort.util.MapUtil;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author: ddh
 * @data: 2020/1/7 9:34
 * @description
 **/
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController extends BaseController {
    private final TokenUtil tokenUtil;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(TokenUtil tokenUtil, EmployeeService employeeService) {
        this.tokenUtil = tokenUtil;
        this.employeeService = employeeService;
    }

    /**
     * 创建员工
     *
     * @param token      用户 token
     * @param employeeVO 员工实体类
     * @param result     参数校验
     * @return Response
     */
    @RequiredPermission
    @PostMapping
    public Response createEmployee(@RequestHeader("token") String token,
                                   @Valid @RequestBody EmployeeVO employeeVO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
            String employeeId = UUIDUtil.getUUID();
            Employee employee = employeeService.createEmployee(Employee.builder()
                    .employeeId(employeeId)
                    .employeeCard(employeeVO.getEmployeeCard())
                    .enterpriseId(enterpriseId)
                    .employeeSex(employeeVO.getEmployeeSex())
                    .employeeAge(employeeVO.getEmployeeAge())
                    .employeePhone(employeeVO.getEmployeePhone())
                    .employeeName(employeeVO.getEmployeeName())
                    .createTime(System.currentTimeMillis())
                    .updateTime(System.currentTimeMillis()).build());
            if (employee != null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
            } else {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }

    /**
     * 分页获取员工信息
     *
     * @param token 用户 token
     * @param page  页数
     * @param size  页面大小
     * @return Response
     */
    @RequiredPermission
    @GetMapping
    public Response getAllEmployee(@RequestHeader("token") String token,
                                   @RequestParam(value = "page", defaultValue = Const.DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                   @RequestParam(value = "size", defaultValue = Const.DEFAULT_PAGE_SIZE, required = false) Integer size) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Page<Employee> employees = employeeService.getAllEmployeeByEnterpriseId(enterpriseId, PageRequest.of(page, size));
        if (employees != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS,
                    employees.map(e -> EmployeeVO.builder()
                            .employeeCard(e.getEmployeeCard())
                            .employeeName(e.getEmployeeName())
                            .employeeAge(e.getEmployeeAge())
                            .employeeSex(e.getEmployeeSex())
                            .employeePhone(e.getEmployeePhone()).build()));
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }

    /**
     * 通过员工卡号删除指定员工信息
     *
     * @param token        用户 token
     * @param employeeCard 员工卡号
     * @return Response
     */
    @RequiredPermission
    @DeleteMapping("/{employeeCard}")
    public Response deleteEmployeeByCard(@RequestHeader("token") String token,
                                         @PathVariable @NotBlank String employeeCard) {
        Integer row = employeeService.deleteEmployeeByEmployeeCard(employeeCard);
        if (row > 0) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, MapUtil.create("row", row + ""));
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }
    }

    /**
     * 通过员工卡号删修改指定员工信息
     *
     * @param token      用户 token
     * @param employeeVO 员工实体类
     * @param result     参数校验
     * @return Response
     */
    @RequiredPermission
    @PutMapping
    public Response updateEmployeeByCard(@RequestHeader("token") String token,
                                         @Valid @RequestBody EmployeeVO employeeVO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        }
        Integer row = employeeService.updateEmployeeByEmployeeCard(Employee.builder()
                .employeeCard(employeeVO.getEmployeeCard())
                .employeeName(employeeVO.getEmployeeName())
                .employeeAge(employeeVO.getEmployeeAge())
                .employeeSex(employeeVO.getEmployeeSex())
                .employeePhone(employeeVO.getEmployeePhone())
                .updateTime(System.currentTimeMillis())
                .build());
        if (row > 0) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, MapUtil.create("row", row + ""));
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }
    }
}
