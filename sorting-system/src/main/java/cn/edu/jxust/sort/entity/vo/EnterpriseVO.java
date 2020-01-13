package cn.edu.jxust.sort.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author: ddh
 * @data: 2020/1/4 20:13
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseVO {
    private String enterpriseId;

    @NotEmpty
    private String enterpriseName;

    @NotEmpty
    private String enterpriseAddr;

    @NotEmpty
    private String enterpriseEmail;
}
