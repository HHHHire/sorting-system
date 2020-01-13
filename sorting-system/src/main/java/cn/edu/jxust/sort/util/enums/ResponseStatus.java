package cn.edu.jxust.sort.util.enums;

/**
 * @author: ddh
 * @data: 2020/1/3 9:50
 * @description
 **/
@SuppressWarnings("unused")
public enum ResponseStatus {
    /**
     * 状态枚举
     */
    SUCCESS(200, "请求成功"),
    FAILED(400, "请求失败"),
    PARAM_ERROR(401, "参数错误"),
    NEED_LOGIN(402, "未登录"),
    VISITED_FORBID(403, "权限不足"),
    NOT_FOUND(404, "资源未找到"),
    SYSTEM_ERROR(500, "系统错误");

    private Integer code;
    private String msg;

    ResponseStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
