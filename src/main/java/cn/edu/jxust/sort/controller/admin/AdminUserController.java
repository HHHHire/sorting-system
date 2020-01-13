package cn.edu.jxust.sort.controller.admin;

import cn.edu.jxust.sort.entity.po.Account;
import cn.edu.jxust.sort.entity.po.Token;
import cn.edu.jxust.sort.entity.po.User;
import cn.edu.jxust.sort.entity.vo.UserInfoVO;
import cn.edu.jxust.sort.entity.vo.UserRegisterVO;
import cn.edu.jxust.sort.service.AccountService;
import cn.edu.jxust.sort.service.EnterpriseService;
import cn.edu.jxust.sort.service.TokenService;
import cn.edu.jxust.sort.service.UserService;
import cn.edu.jxust.sort.util.*;
import cn.edu.jxust.sort.util.common.Const;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.enums.Role;
import cn.edu.jxust.sort.util.models.Response;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/5 20:56
 * @description admin user controller
 **/
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    private final TokenUtil tokenUtil;
    private final UserService userService;
    private final TokenService tokenService;
    private final AccountService accountService;
    private final EnterpriseService enterpriseService;

    public AdminUserController(TokenUtil tokenUtil, UserService userService, EnterpriseService enterpriseService, AccountService accountService, TokenService tokenService) {
        this.tokenUtil = tokenUtil;
        this.userService = userService;
        this.enterpriseService = enterpriseService;
        this.accountService = accountService;
        this.tokenService = tokenService;
    }

    /**
     * 管理员创建用户，返回创建的状态
     *
     * @param registerVO 用户实体
     * @param result     参数校验结果
     * @return Response
     */
    @PostMapping
    public Response createUser(@Valid @RequestBody UserRegisterVO registerVO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            String userName = registerVO.getUserName();
            User user = userService.getUserByUserName(userName);
            if (user == null) {
                String userId = UUIDUtil.getUUID();
                String enterpriseId = registerVO.getEnterpriseId();
                try {
                    userService.createUser(User.builder()
                            .userId(userId)
                            .userName(registerVO.getUserName())
                            .userEmail(registerVO.getUserEmail())
                            .userPhone(registerVO.getUserPhone())
                            .enterpriseId(enterpriseId)
                            .createTime(System.currentTimeMillis())
                            .updateTime(System.currentTimeMillis())
                            .build());
                    accountService.createAccount(Account.builder()
                            .userId(userId)
                            .userName(registerVO.getUserName())
                            .userPwd(EncryptionUtil.encrypt(registerVO.getUserPwd()))
                            .userRole(Role.USER)
                            .createTime(System.currentTimeMillis())
                            .updateTime(System.currentTimeMillis())
                            .build());
                    tokenService.createToken(Token.builder()
                            .userId(userId)
                            .userToken(tokenUtil.createJwt(MapUtil.create(
                                    "userId", userId,
                                    "enterpriseId", enterpriseId)))
                            .createTime(System.currentTimeMillis())
                            .updateTime(System.currentTimeMillis())
                            .build());
                    return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
                } catch (JWTCreationException e) {
                    e.printStackTrace();
                    return ResponseUtil.responseWithoutData(ResponseStatus.SYSTEM_ERROR);
                }
            } else {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED, "用户名已存在");
            }
        }
    }

    /**
     * 查询所有用户
     *
     * @param session      session
     * @param enterpriseId 企业id
     * @param page         当前页
     * @param size         每页显示条数
     * @return Response
     */
    @GetMapping
    public Response getAllUserByPaging(HttpSession session, @RequestParam(value = "enterpriseId", required = false) String enterpriseId,
                                       @RequestParam(value = "page", defaultValue = Const.DEFAULT_PAGE_NUMBER) Integer page,
                                       @RequestParam(value = "size", defaultValue = Const.DEFAULT_PAGE_SIZE) Integer size) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else {
            Page<User> users = userService.getAllUser(enterpriseId, PageRequest.of(page, size));
            if (users == null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            } else {
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, users);
            }
        }
    }

    /**
     * 通过用户id获取用户详情
     *
     * @param userId  用户id
     * @param session session
     * @return Response
     */
    @GetMapping("/{userId}")
    public Response getUserById(@PathVariable String userId, HttpSession session) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else {
            Token token = tokenService.getTokenById(userId);
            if (token == null) {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
            User user = userService.getUserById(userId);
            if (user != null) {
                UserInfoVO userInfoVO = UserInfoVO.builder()
                        .userId(userId)
                        .userName(user.getUserName())
                        .userEmail(user.getUserEmail())
                        .userToken(token.getUserToken())
                        .userPhone(user.getUserPhone())
                        .enterpriseName(enterpriseService.getEnterpriseById(user.getEnterpriseId()).getEnterpriseName())
                        .createTime(user.getCreateTime())
                        .updateTime(user.getUpdateTime())
                        .build();
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, userInfoVO);
            } else {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }

    /**
     * 重置用户密码且可以修改电话和邮箱
     *
     * @param userInfoVO 用户信息实体类
     * @param session    session
     * @param result     参数校验
     * @return Response
     */
    @PutMapping
    public Response updateUser(@Valid @RequestBody UserInfoVO userInfoVO, HttpSession session, BindingResult result) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            User user = userService.getUserById(userInfoVO.getUserId());
            if (user != null) {
                try {
                    userService.createUser(User.builder()
                            .userId(userInfoVO.getUserId())
                            .userName(userInfoVO.getUserName())
                            .userEmail(userInfoVO.getUserEmail())
                            .userPhone(userInfoVO.getUserPhone())
                            .enterpriseId(user.getEnterpriseId())
                            .updateTime(System.currentTimeMillis())
                            .build());
                    accountService.createAccount(Account.builder()
                            .userId(userInfoVO.getUserId())
                            .userName(userInfoVO.getUserName())
                            .userPwd(EncryptionUtil.encrypt(Const.DEFAULT_USER_PASSWORD))
                            .userRole(Role.USER)
                            .updateTime(System.currentTimeMillis())
                            .build());
                    return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
                }
            } else {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }

    /**
     * 删除用户
     *
     * @param userId  用户id
     * @param session session
     * @return Response
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{userId}")
    public Response deleteUserById(@PathVariable String userId, HttpSession session) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else if (userId == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            try {
                userService.deleteUserByUserId(userId);
                tokenService.deleteTokenByUserId(userId);
                accountService.deleteAccountByUserId(userId);
                return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }

    /**
     * 查询企业所有的用户
     *
     * @param enterpriseId 企业 id
     * @param session      session
     * @return Response
     */
    @GetMapping("/enterprise/{enterpriseId}")
    public Response getUserByEnterpriseId(@PathVariable String enterpriseId, HttpSession session) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else if (enterpriseId == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            try {
                List<User> users = userService.getUserByEnterpriseId(enterpriseId);
                if (users == null) {
                    return ResponseUtil.responseWithoutData(ResponseStatus.FAILED, "没有查询结果");
                } else {
                    return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, users);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }
}
