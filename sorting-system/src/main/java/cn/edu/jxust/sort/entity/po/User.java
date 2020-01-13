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
 * @data: 2020/1/3 10:11
 * @description
 **/
@Entity(name = "ss_user")
@Table(appliesTo = "ss_user", comment = "用户表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 用户 ID
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '用户id'")
    private String userId;
    /**
     * 用户名
     */
    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) comment '用户名'")
    private String userName;
    /**
     * 用户手机号
     */
    @Column(columnDefinition = "varchar(100) comment '用户手机号'")
    private String userPhone;
    /**
     * 用户邮箱
     */
    @Column(columnDefinition = "varchar(100) comment '用户邮箱'")
    private String userEmail;

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
