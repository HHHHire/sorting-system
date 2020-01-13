package cn.edu.jxust.sort.controller.api;

import cn.edu.jxust.sort.entity.po.Device;
import cn.edu.jxust.sort.entity.vo.DeviceVO;
import cn.edu.jxust.sort.service.DeviceService;
import cn.edu.jxust.sort.util.MapUtil;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.TokenUtil;
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
 * @data: 2020/1/7 10:31
 * @description
 **/
@RestController
@RequestMapping("/api/device")
public class DeviceController extends BaseController {
    private final TokenUtil tokenUtil;
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(TokenUtil tokenUtil, DeviceService deviceService) {
        this.tokenUtil = tokenUtil;
        this.deviceService = deviceService;
    }

    /**
     * 分页获取所有设备信息
     *
     * @param token 用户 token
     * @return Response
     */
    @RequiredPermission
    @GetMapping
    public Response getAllDevice(@RequestHeader("token") String token,
                                 @RequestParam(value = "page", defaultValue = Const.DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                 @RequestParam(value = "size", defaultValue = Const.DEFAULT_PAGE_SIZE, required = false) Integer size) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Page<Device> devices = deviceService.getAlldeviceByEnterpriseId(enterpriseId, PageRequest.of(page, size));
        if (devices != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS,
                    devices.map(e -> DeviceVO.builder()
                            .deviceId(e.getDeviceId())
                            .deviceName(e.getDeviceName())
                            .createTime(e.getCreateTime()).build()));
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }

    /**
     * 创建设备
     *
     * @param token    用户 token
     * @param deviceVO 设备实体类
     * @param result   参数校验
     * @return Response
     */
    @RequiredPermission
    @PostMapping
    public Response createDevice(@RequestHeader("token") String token,
                                 @Valid @RequestBody DeviceVO deviceVO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.responseWithoutData(ResponseStatus.PARAM_ERROR);
        } else {
            String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
            Device device = deviceService.createDevice(Device.builder()
                    .deviceId(deviceVO.getDeviceId())
                    .deviceName(deviceVO.getDeviceName())
                    .enterpriseId(enterpriseId)
                    .createTime(System.currentTimeMillis())
                    .updateTime(System.currentTimeMillis())
                    .build());
            if (device != null) {
                return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, device);
            } else {
                return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
            }
        }
    }

    /**
     * 通过设备 id 删除设备
     *
     * @param token    用户 token
     * @param deviceId 设备 id
     * @return Response
     */
    @RequiredPermission
    @DeleteMapping("/{deviceId}")
    public Response deleteDeviceById(@RequestHeader("token") String token,
                                     @PathVariable @NotBlank String deviceId) {
        Integer row = deviceService.deleteDeviceByDeviceId(deviceId);
        if (row > 0) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, MapUtil.create("row", row + ""));
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.FAILED);
        }
    }
}
