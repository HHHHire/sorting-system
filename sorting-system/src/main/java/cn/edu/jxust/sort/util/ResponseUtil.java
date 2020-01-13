package cn.edu.jxust.sort.util;

import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import cn.edu.jxust.sort.util.models.ResponseData;
import cn.edu.jxust.sort.util.models.ResponseMsg;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;

/**
 * @author: ddh
 * @data: 2020/1/3 11:18
 * @description
 **/
public class ResponseUtil {
    /**
     * 返回响应消息
     *
     * @param status 响应状态
     * @param msg    响应信息
     * @return Response
     */
    public static Response responseWithoutData(ResponseStatus status, String msg) {
        return ResponseMsg.builder()
                .code(status.getCode())
                .msg(msg)
                .build();
    }

    public static Response responseWithoutData(ResponseStatus status) {
        return responseWithoutData(status, status.getMsg());
    }

    /**
     * 返回消息和数据
     *
     * @param status 响应状态
     * @param msg    响应消息
     * @param data   响应数据
     * @return Response
     */
    public static Response responseWithData(ResponseStatus status, String msg, Object data) {
        return ResponseData.builder()
                .code(status.getCode())
                .msg(status.getMsg())
                .data(data)
                .build();
    }

    public static Response responseWithData(ResponseStatus status, Object data) {
        return responseWithData(status, status.getMsg(), data);
    }

    public static ResponseMessage getMessage(ResponseStatus status) {
        return new ResponseMessageBuilder().build();
    }
}
