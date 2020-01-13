package cn.edu.jxust.sort.controller.admin;

import cn.edu.jxust.sort.entity.po.Account;
import cn.edu.jxust.sort.entity.vo.AdminVO;
import cn.edu.jxust.sort.service.AccountService;
import cn.edu.jxust.sort.util.EncryptionUtil;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.common.Const;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.enums.Role;
import cn.edu.jxust.sort.util.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author: ddh
 * @data: 2020/1/3 11:23
 * @description admin account controller
 **/
@RestController
@RequestMapping("/admin/account")
public class AdminAccountController {
    private final AccountService accountService;

    @Autowired
    public AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 管理员登录
     *
     * @param adminVO     登录实体类
     * @param httpSession session
     * @param result      参数校验
     * @return Response
     */
    @PostMapping
    public Response adminLogin(@Valid @RequestBody AdminVO adminVO, HttpSession httpSession, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            Account account = accountService.getLogin(adminVO.getAdminName(), EncryptionUtil.encrypt(adminVO.getAdminPwd()));
            if (account == null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED, "登录失败");
            }
            // 判断是否是管理员权限
            else if (account.getUserRole() != Role.ADMIN) {
                return ResponseUtil.responseWithoutData(ResponseStatus.VISITED_FORBID);
            } else {
                // 有该管理员账户信息，且登陆成功
                httpSession.setAttribute(Const.CURRENT_ADMIN, account);
                return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
            }
        }
    }

    /**
     * 退出登录
     *
     * @param session session
     * @return Response
     */
    @PostMapping("/logout")
    public Response adminLogout(HttpSession session) {
        try {
            session.removeAttribute(Const.CURRENT_ADMIN);
            return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }

    }
}
