package cn.edu.jxust.sort.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: ddh
 * @data: 2020/1/5 21:28
 * @description
 **/
@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    @ApiModelProperty(name = "用户名", required = true)
    @NotBlank
    private String userName;
    @ApiModelProperty(name = "密码", required = true)
    @NotBlank
    private String userPwd;
}
