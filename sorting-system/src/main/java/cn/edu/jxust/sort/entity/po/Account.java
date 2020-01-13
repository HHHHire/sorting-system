package cn.edu.jxust.sort.entity.po;

import cn.edu.jxust.sort.util.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: ddh
 * @data: 2020/1/3 9:37
 * @description 账户实体
 **/
@Entity(name = "ss_account")
@Table(appliesTo = "ss_account", comment = "账户表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    /**
     * 用户 ID
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '用户id'")
    private String userId;
    /**
     * 账户用户名，非空且唯一
     */
    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) comment '用户名'")
    private String userName;
    /**
     * 账户密码，非空
     */
    @Column(nullable = false, columnDefinition = "varchar(255) comment '用户密码'")
    private String userPwd;
    /**
     * 账户角色
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) comment '用户权限'")
    private Role userRole;
    /**
     * 创建日期
     */
    @Column(updatable = false, columnDefinition = "bigint(20) comment'创建时间'")
    @CreatedDate
    private Long createTime;
    /**
     * 修改日期
     */
    @LastModifiedDate
    @Column(columnDefinition = "bigint(20) comment'创建时间'")
    private Long updateTime;
}
