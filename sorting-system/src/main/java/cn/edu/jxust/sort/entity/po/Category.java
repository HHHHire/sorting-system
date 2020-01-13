package cn.edu.jxust.sort.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ddh
 * @data: 2020/1/3 10:28
 * @description
 **/
@Entity(name = "ss_category")
@Table(appliesTo = "ss_category", comment = "分类表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category implements Serializable {
    /**
     * 分类编码
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '分类编号'")
    private String categoryId;
    /**
     * 分类名称
     */
    @Column(nullable = false, columnDefinition = "varchar(255) comment '分类名称'")
    private String categoryName;
    /**
     * 长度
     */
    @Column(columnDefinition = "decimal(10,4) comment '长度'")
    private BigDecimal length;
    /**
     * 长度公差
     */
    @Column(columnDefinition = "decimal(10,4) comment '长度公差'")
    private BigDecimal lengthTolerance;
    /**
     * 重量
     */
    @Column(columnDefinition = "decimal(10,4) comment '重量'")
    private BigDecimal weight;
    /**
     * 重量公差
     */
    @Column(columnDefinition = "decimal(10,4) comment '重量公差'")
    private BigDecimal weightTolerance;
    /**
     * 企业 id
     */
    @Column(columnDefinition = "varchar(255) comment '企业id'")
    private String enterpriseId;
    /**
     * 设备 id
     */
    @Column(columnDefinition = "varchar(255) comment '设备id'")
    private String deviceId;
    /**
     * 创建日期
     */
    @Column(columnDefinition = "bigint(20) comment '创建日期'")
    @CreatedDate
    private Long createTime;
    /**
     * 修改日期
     */
    @Column(columnDefinition = "bigint(20) comment '修改日期'")
    @LastModifiedDate
    private Long updateTime;
}
