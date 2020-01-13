package cn.edu.jxust.sort.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: ddh
 * @data: 2020/1/3 10:10
 * @description
 **/
@Entity(name = "ss_employee")
@Table(appliesTo = "ss_employee", comment = "用户表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    /**
     * 员工 ID
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '员工id'")
    private String employeeId;
    /**
     * 员工卡号
     */
    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) comment '管理员id'")
    private String employeeCard;
    /**
     * 员工名字
     */
    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) comment '管理员id'")
    private String employeeName;
    /**
     * 员工年龄
     */
    @Column(columnDefinition = "varchar(20) comment '员工年龄'")
    private String employeeAge;
    /**
     * 员工性别
     */
    @Column(columnDefinition = "varchar(20) comment '员工性别'")
    private String employeeSex;
    /**
     * 用户手机号
     */
    @Column(columnDefinition = "varchar(20) comment '用户手机号'")
    private String employeePhone;
    /**
     * 所属企业id 非空
     */
    @Column(nullable = false, columnDefinition = "varchar(255) comment '所属企业id'")
    private String enterpriseId;
    /**
     * 创建日期
     */
    @Column(updatable = false, columnDefinition = "bigint(20) comment '创建日期'")
    @CreatedDate
    private Long createTime;
    /**
     * 修改日期
     */
    @LastModifiedDate
    @Column(columnDefinition = "bigint(20) comment'更新时间'")
    private Long updateTime;
}
