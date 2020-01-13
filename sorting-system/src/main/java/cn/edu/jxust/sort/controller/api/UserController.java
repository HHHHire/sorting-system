package cn.edu.jxust.sort.controller.api;

import cn.edu.jxust.sort.entity.po.Enterprise;
import cn.edu.jxust.sort.entity.po.Token;
import cn.edu.jxust.sort.entity.po.User;
import cn.edu.jxust.sort.entity.vo.UserInfoVO;
import cn.edu.jxust.sort.service.EnterpriseService;
import cn.edu.jxust.sort.service.TokenService;
import cn.edu.jxust.sort.service.UserService;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.TokenUtil;
import cn.edu.jxust.sort.util.annotations.RequiredPermission;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ddh
 * @data: 2020/1/6 9:55
 * @description
 **/
@Api(value = "用户相关接口", tags = "user")
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController  {
    private final TokenUtil tokenUtil;
    private final TokenService tokenService;
    private final UserService userService;
    private final EnterpriseService enterpriseService;

    public UserController(TokenUtil tokenUtil, TokenService tokenService, UserService userService, EnterpriseService enterpriseService) {
        this.tokenUtil = tokenUtil;
        this.tokenService = tokenService;
        this.userService = userService;
        this.enterpriseService = enterpriseService;
    }

    @RequiredPermission
    @GetMapping
    public Response getUser(@RequestHeader("token") String token) {
        String userId = tokenUtil.getClaim(token, "userId").asString();
        User user = userService.getUserById(userId);
        Token tok = tokenService.getTokenById(userId);
        if (user != null) {
            Enterprise enterprise = enterpriseService.getEnterpriseById(user.getEnterpriseId());
            UserInfoVO userInfoVO = UserInfoVO.builder()
                    .userId(userId)
                    .userName(user.getUserName())
                    .userEmail(user.getUserEmail())
                    .userPhone(user.getUserPhone())
                    .userToken(tok.getUserToken())
                    .enterpriseName(enterprise.getEnterpriseName())
                    .createTime(user.getCreateTime())
                    .updateTime(user.getUpdateTime())
                    .build();
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, userInfoVO);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }
}
