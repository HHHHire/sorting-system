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
@Entity(name = "ss_enterprise")
@Table(appliesTo = "ss_enterprise", comment = "企业表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise implements Serializable {
    /**
     * 企业id
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '企业id'")
    private String enterpriseId;
    /**
     * 企业名称，非空且唯一
     */
    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) comment '企业名称'")
    private String enterpriseName;
    /**
     * 企业地址
     */
    @Column(columnDefinition = "varchar(255) comment '企业地址'")
    private String enterpriseAddr;
    /**
     * 企业邮箱
     */
    @Column(columnDefinition = "varchar(255) comment '企业邮箱'")
    private String enterpriseEmail;
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
    @Column(columnDefinition = "bigint(20) comment '修改日期'")
    private Long updateTime;
}
