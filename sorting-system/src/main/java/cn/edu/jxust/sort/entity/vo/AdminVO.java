package cn.edu.jxust.sort.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: ddh
 * @data: 2020/1/3 11:29
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminVO {

    @NotBlank
    private String adminName;

    @NotBlank
    private String adminPwd;
}
