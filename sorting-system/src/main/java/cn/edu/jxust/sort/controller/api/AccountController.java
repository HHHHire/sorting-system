package cn.edu.jxust.sort.controller.api;

import cn.edu.jxust.sort.entity.po.Account;
import cn.edu.jxust.sort.entity.po.Token;
import cn.edu.jxust.sort.entity.vo.UserVO;
import cn.edu.jxust.sort.service.AccountService;
import cn.edu.jxust.sort.service.TokenService;
import cn.edu.jxust.sort.util.EncryptionUtil;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author: ddh
 * @data: 2020/1/5 21:02
 * @description
 **/
@RestController
@RequestMapping("/api/account")
public class AccountController extends BaseController {
    private final TokenService tokenService;
    private final AccountService accountService;

    @Autowired
    public AccountController(TokenService tokenService, AccountService accountService) {
        this.tokenService = tokenService;
        this.accountService = accountService;
    }

    /**
     * 用户登录，获取 token
     *
     * @param userVO 登录实体类
     * @param result 参数校验
     * @return Response
     */
    @PostMapping
    public Response userLogin(@Valid @RequestBody @ApiParam(name = "用户账户", value = "账户实体，包括用户名和密码", required = true) UserVO userVO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            Account login = accountService.getLogin(userVO.getUserName(), EncryptionUtil.encrypt(userVO.getUserPwd()));
            if (login != null) {
                Token token = tokenService.getTokenById(login.getUserId());
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, token);
            } else {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED, "用户名或密码错误");
            }
        }
    }
}
