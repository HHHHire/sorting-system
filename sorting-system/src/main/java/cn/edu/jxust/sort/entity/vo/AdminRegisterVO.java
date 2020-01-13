package cn.edu.jxust.sort.entity.vo;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: ddh
 * @data: 2020/1/3 14:50
 * @description
 **/
@Data
@Builder
public class AdminRegisterVO {
    /**
     * 管理员姓名
     */
    @NotBlank
    private String adminName;
    /**
     * 账户密码，非空
     */
    @NotBlank
    private String adminPwd;
    /**
     * 管理员邮箱
     */
    @NotBlank
    private String adminEmail;
    /**
     * 管理员号码
     */
    @NotBlank
    private String adminPhone;
}
