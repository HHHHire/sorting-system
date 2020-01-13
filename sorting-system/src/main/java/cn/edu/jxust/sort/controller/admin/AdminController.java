package cn.edu.jxust.sort.controller.admin;

import cn.edu.jxust.sort.entity.po.Account;
import cn.edu.jxust.sort.entity.po.Admin;
import cn.edu.jxust.sort.entity.vo.AdminRegisterVO;
import cn.edu.jxust.sort.service.AccountService;
import cn.edu.jxust.sort.service.AdminService;
import cn.edu.jxust.sort.util.EncryptionUtil;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.UUIDUtil;
import cn.edu.jxust.sort.util.common.Const;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.enums.Role;
import cn.edu.jxust.sort.util.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/3 14:40
 * @description admin controller
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AccountService accountService;
    private final AdminService adminService;

    @Autowired
    public AdminController(AccountService accountService, AdminService adminService) {
        this.accountService = accountService;
        this.adminService = adminService;
    }

    /**
     * 创建管理员
     *
     * @param adminRegisterVO 管理员实体
     * @param session         session
     * @param result          校验结果
     * @return Response
     */
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public Response createAdmin(@Valid @RequestBody AdminRegisterVO adminRegisterVO, HttpSession session, BindingResult result) {
        Account account = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else if (account == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else {
            try {
                String adminId = UUIDUtil.getUUID();
                Admin admin = adminService.createAdmin(Admin.builder()
                        .adminId(adminId)
                        .adminName(adminRegisterVO.getAdminName())
                        .adminEmail(adminRegisterVO.getAdminEmail())
                        .adminPhone(adminRegisterVO.getAdminPhone())
                        .createTime(System.currentTimeMillis())
                        .updateTime(System.currentTimeMillis())
                        .build());
                if (admin == null) {
                    return ResponseUtil.responseWithoutData(ResponseStatus.FAILED, "该管理员名字已经存在");
                }
                accountService.createAccount(Account.builder()
                        .userId(adminId)
                        .userName(adminRegisterVO.getAdminName())
                        .userPwd(EncryptionUtil.encrypt(adminRegisterVO.getAdminPwd()))
                        .userRole(Role.ADMIN)
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

    /**
     * 获取管理员信息
     *
     * @param session 管理员id
     * @return Response
     */
    @GetMapping
    public Response getAdminInfo(HttpSession session) {
        Account account = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (account == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else {
            Admin admin = adminService.getAdmin(account.getUserId());
            if (admin == null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            } else {
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, admin);
            }
        }
    }

    /**
     * 修改管理员信息
     *
     * @param registerVO 管理员实体
     * @param result     参数校验结果
     * @return Response
     */
    @PutMapping
    public Response updateAdmin(@Valid @RequestBody AdminRegisterVO registerVO, BindingResult result, HttpSession session) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            try {
                accountService.createAccount(Account.builder()
                        .userId(admin.getUserId())
                        .userName(admin.getUserName())
                        .userPwd(EncryptionUtil.encrypt(registerVO.getAdminPwd()))
                        .userRole(Role.ADMIN)
                        .build());
                adminService.createAdmin(Admin.builder()
                        .adminId(admin.getUserId())
                        .adminName(admin.getUserName())
                        .adminPhone(registerVO.getAdminPhone())
                        .adminEmail(registerVO.getAdminEmail())
                        .build());
                return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }

    /**
     * 获取所有管理员的信息
     *
     * @param session session
     * @return Response
     */
    @GetMapping("/all")
    public Response getAllAdmin(HttpSession session) {
        Account account = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (account == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else {
            List<Admin> admins = adminService.getAllAdmin();
            if (admins == null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            } else {
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, admins);
            }
        }
    }

    /**
     * 删除管理员
     *
     * @param adminId 管理员id
     * @param session session
     * @return Response
     */
    @DeleteMapping("/{adminId}")
    public Response deleteAdminById(@PathVariable("adminId") String adminId, HttpSession session) {
        Account account = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (account == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else {
            try {
                accountService.deleteAccountByUserId(adminId);
                adminService.deleteAdminById(adminId);
                return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }
}
