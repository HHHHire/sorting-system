package cn.edu.jxust.sort.controller.admin;

import cn.edu.jxust.sort.entity.po.Account;
import cn.edu.jxust.sort.entity.po.Binding;
import cn.edu.jxust.sort.entity.vo.BindingVO;
import cn.edu.jxust.sort.mqtt.MqClient;
import cn.edu.jxust.sort.mqtt.handler.MessageArrivedHandler;
import cn.edu.jxust.sort.service.BindingService;
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
 * @data: 2020/1/4 20:05
 * @description
 **/
@Slf4j
@RestController
@RequestMapping("/admin/binding")
public class AdminBindingController {
    private final BindingService bindingService;
    private final MessageArrivedHandler handler;

    @Autowired
    public AdminBindingController(BindingService bindingService, MessageArrivedHandler handler) {
        this.bindingService = bindingService;
        this.handler = handler;
    }

    /**
     * 新建绑定
     *
     * @param bindingVO 绑定实体
     * @param result    参数校验
     * @param session   session
     * @return Response
     */
    @PostMapping
    public Response createBind(@Valid @RequestBody BindingVO bindingVO, BindingResult result, HttpSession session) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            Boolean exist = bindingService.isExist(bindingVO.getDeviceId());
            if (exist) {
                String clientId = UUIDUtil.getUUID();
                try {
                    Binding bind = bindingService.createBind(Binding.builder()
                            .accessKey(bindingVO.getAccessKey())
                            .clientId(clientId)
                            .mqId(bindingVO.getMqId())
                            .topic(bindingVO.getTopic())
                            .subscribe(bindingVO.getSubscribe())
                            .deviceId(bindingVO.getDeviceId()).build());
                    MqClient mqClient = new MqClient(bindingVO.getMqId()
                            , bindingVO.getTopic()
                            , bindingVO.getSubscribe()
                            , bindingVO.getAccessKey()
                            , clientId
                            , handler);
                    mqClient.connect();
                    log.info("绑定成功");

                    if (bind == null) {
                        log.error("设备绑定信息存储失败");
                        return ResponseUtil.responseWithData(ResponseStatus.FAILED, "设备绑定信息存储失败");
                    }
                    return ResponseUtil.responseWithoutData(ResponseStatus.SUCCESS);
                } catch (Exception e) {
                    log.error("设备绑定异常", e);
                    return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
                }
            } else {
                return ResponseUtil.responseWithData(ResponseStatus.SYSTEM_ERROR, "在OneNET平台中这个设备不存在，请检查设备ID是否正确");
            }
        }
    }

    /**
     * 分页获取绑定信息
     *
     * @param session session
     * @param page    页数
     * @param size    页面大小
     * @return Response
     */
    @GetMapping
    public Response getAllBound(HttpSession session
            , @RequestParam(value = "page", defaultValue = Const.DEFAULT_PAGE_NUMBER) Integer page
            , @RequestParam(value = "size", defaultValue = Const.DEFAULT_PAGE_SIZE) Integer size) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else {
            Page<Binding> boundByPaging = bindingService.getBoundByPaging(PageRequest.of(page, size));
            if (boundByPaging != null) {
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, boundByPaging);
            } else {
                return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
            }
        }
    }

    /**
     * 删除绑定信息
     *
     * @param session  session
     * @param clientId mq客户端ID
     * @return Response
     */
    @DeleteMapping("/{clientId}")
    public Response deleteByClientId(HttpSession session, @PathVariable String clientId) {
        Account admin = (Account) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.NEED_LOGIN);
        } else if (clientId == null) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            Boolean result = bindingService.deleteBoundByClientId(clientId);
            if (result) {
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, "删除成功");
            } else {
                return ResponseUtil.responseWithData(ResponseStatus.FAILED, "删除失败");
            }
        }
    }
}
