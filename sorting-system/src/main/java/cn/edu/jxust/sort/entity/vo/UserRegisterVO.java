package cn.edu.jxust.sort.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: ddh
 * @data: 2020/1/5 21:43
 * @description
 **/
@Data
public class UserRegisterVO {
    @NotBlank
    private String userName;

    @NotBlank
    private String userPwd;

    @NotBlank
    private String userEmail;

    @NotBlank
    private String userPhone;

    @NotBlank
    private String enterpriseId;
}
