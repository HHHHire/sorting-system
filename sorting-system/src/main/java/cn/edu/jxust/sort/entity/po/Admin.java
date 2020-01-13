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
 * @data: 2020/1/3 10:09
 * @description
 **/
@Entity(name = "ss_admin")
@Table(appliesTo = "ss_admin", comment = "管理员表")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Admin implements Serializable {
    /**
     * 管理员ID
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '管理员id'")
    private String adminId;
    /**
     * 管理员姓名
     */
    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) comment '管理员用户名'")
    private String adminName;
    /**
     * 管理员邮箱
     */
    @Column(columnDefinition = "varchar(255) comment '管理员邮箱'")
    private String adminEmail;
    /**
     * 管理员号码
     */
    @Column(columnDefinition = "varchar(20) comment '管理员电话'")
    private String adminPhone;
    /**
     * 创建日期
     */
    @Column(updatable = false, columnDefinition = "bigint(20) comment '创建时间'")
    @CreatedDate
    private Long createTime;
    /**
     * 修改日期
     */
    @LastModifiedDate
    @Column(columnDefinition = "bigint(20) comment '更新时间'")
    private Long updateTime;
}
