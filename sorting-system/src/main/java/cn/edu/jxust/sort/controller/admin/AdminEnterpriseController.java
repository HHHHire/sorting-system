package cn.edu.jxust.sort.controller.admin;

import cn.edu.jxust.sort.entity.po.Account;
import cn.edu.jxust.sort.entity.po.Enterprise;
import cn.edu.jxust.sort.entity.vo.EnterpriseVO;
import cn.edu.jxust.sort.service.DeviceService;
import cn.edu.jxust.sort.service.EmployeeService;
import cn.edu.jxust.sort.service.EnterpriseService;
import cn.edu.jxust.sort.service.UserService;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.UUIDUtil;
import cn.edu.jxust.sort.util.common.Const;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author: ddh
 * @data: 2020/1/4 20:07
 * @description admin enterprise controller
 **/
@Slf4j
@RestController
@RequestMapping("/admin/enterprise")
public class AdminEnterpriseController {
    private final EnterpriseService enterPriseService;
    private final DeviceService deviceService;
    private final UserService userService;
    private final EmployeeService employeeService;

    @Autowired
    public AdminEnterpriseController(EnterpriseService enterPriseService, DeviceService deviceService, UserService userService, EmployeeService employeeService) {
        this.enterPriseService = enterPriseService;
        this.deviceService = deviceService;
        this.userService = userService;
        this.employeeService = employeeService;
    }

    /**
     * 新建企业
     *
     * @param enterpriseVO 企业VO
     * @param session      session
     * @param result       参数校验结果
     * @return Response
     */
    @PostMapping
    public Response createEnterprise(@Valid @RequestBody EnterpriseVO enterpriseVO, HttpSession session, BindingResult result) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            String enterpriseId = UUIDUtil.getUUID();
            Enterprise enterprise = enterPriseService.getEnterpriseByName(enterpriseVO.getEnterpriseName());
            if (enterprise != null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED, "新建企业失败，企业名称重复");
            } else {
                try {
                    enterPriseService.createEnterprise(Enterprise.builder()
                            .enterpriseId(enterpriseId)
                            .enterpriseName(enterpriseVO.getEnterpriseName())
                            .enterpriseAddr(enterpriseVO.getEnterpriseAddr())
                            .enterpriseEmail(enterpriseVO.getEnterpriseEmail())
                            .createTime(System.currentTimeMillis())
                            .updateTime(System.currentTimeMillis())
                            .build());
                    return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
                }
            }
        }
    }

    /**
     * 分页获取所有企业信息
     *
     * @param page 当前页
     * @param size 每页显示条数
     * @return Response
     */
    @GetMapping
    public Response getAllEnterpriseByPaging(HttpSession session, @RequestParam(value = "page", defaultValue = Const.DEFAULT_PAGE_NUMBER) Integer page,
                                             @RequestParam(value = "size", defaultValue = Const.DEFAULT_PAGE_SIZE) Integer size) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else {
            Page<Enterprise> enterprises = enterPriseService.getAllEnterprise(PageRequest.of(page, size));
            if (enterprises == null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            } else {
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, enterprises);
            }
        }
    }

    /**
     * 通过企业id获取该企业信息
     *
     * @param session      session
     * @param enterpriseId 企业id
     * @return Response
     */
    @GetMapping("/{enterpriseId}")
    public Response getEnterpriseById(HttpSession session, @PathVariable String enterpriseId) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else {
            Enterprise enterprise = enterPriseService.getEnterpriseById(enterpriseId);
            if (enterprise == null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            } else {
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, enterprise);
            }
        }
    }

    /**
     * 通过企业 id 删除指定企业
     *
     * @param enterpriseId 企业 id
     * @param session      session
     * @return Response
     */
    @DeleteMapping("/{enterpriseId}")
    public Response deleteEnterpriseById(@PathVariable String enterpriseId, HttpSession session) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else if (enterpriseId == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            try {
                deviceService.deleteDeviceByEnterpriseId(enterpriseId);
                userService.deleteUserByEnterpriseId(enterpriseId);
                employeeService.deleteEmployeeByEnterpriseId(enterpriseId);
                Integer resultCount = enterPriseService.deleteEnterpriseById(enterpriseId);
                if (resultCount == 0) {
                    return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
                } else {
                    return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }

    /**
     * 修改企业信息
     *
     * @param session session
     * @param enterpriseVO 企业VO实体
     * @param result 参数校验
     * @return Response
     */
    @PutMapping
    public Response updateEnterpriseById(HttpSession session, @Valid @RequestBody EnterpriseVO enterpriseVO, BindingResult result) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            Enterprise enterprise = enterPriseService.getEnterpriseByName(enterpriseVO.getEnterpriseName());
            if (enterprise == null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED, "更新失败");
            }
            try {
                enterPriseService.createEnterprise(Enterprise.builder()
                        .enterpriseId(enterpriseVO.getEnterpriseId())
                        .enterpriseName(enterpriseVO.getEnterpriseName())
                        .enterpriseEmail(enterpriseVO.getEnterpriseEmail())
                        .enterpriseAddr(enterpriseVO.getEnterpriseAddr())
                        .updateTime(System.currentTimeMillis()).build());
                return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }
}
