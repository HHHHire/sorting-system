package cn.edu.jxust.sort.interceptor;

import cn.edu.jxust.sort.service.TokenService;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.TokenUtil;
import cn.edu.jxust.sort.util.annotations.RequiredPermission;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: ddh
 * @data: 2020/1/6 20:41
 * @description 接口拦截器
 **/
@Component
public class ApiHandlerInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    private final TokenUtil tokenUtil;

    @Autowired
    public ApiHandlerInterceptor(TokenService tokenService, TokenUtil tokenUtil) {
        this.tokenService = tokenService;
        this.tokenUtil = tokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            RequiredPermission annotation = method.getMethod().getAnnotation(RequiredPermission.class);
            if (annotation != null) {
                String token = request.getHeader("token");
                if (tokenUtil.isValid(token) && tokenService.isExist(token)) {
                    return true;
                } else {
                    setResponse(response, ResponseUtil.responseWithoutData(ResponseStatus.VISITED_FORBID));
                    return false;
                }
            }
        }
        return true;
    }

    private void setResponse(HttpServletResponse response, Response res) throws IOException {
        response.reset();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json;charset=UTF-8");
        String body = JSONObject.toJSONString(res);
        response.getWriter().write(body);
    }
}
