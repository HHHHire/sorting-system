package cn.edu.jxust.sort.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author: ddh
 * @data: 2020/1/7 9:54
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeVO {
    @NotBlank
    @Excel(name = "员工卡号(工卡卡号)", width = 20)
    private String employeeCard;

    @NotBlank
    @Excel(name = "员工名字", width = 15)
    private String employeeName;

    @Min(value = 18)
    @Excel(name = "员工年龄(18以上)", width = 10)
    private String employeeAge;

    @Excel(name = "员工性别(男或女)", width = 10)
    private String employeeSex;

    @NotBlank
    @Excel(name = "员工手机号", width = 20)
    private String employeePhone;
}
