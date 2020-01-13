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

/**
 * @author: ddh
 * @data: 2020/1/8 15:00
 * @description
 **/
@Entity(name = "ss_inventory")
@Table(appliesTo = "ss_inventory", comment = "库存表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Inventory implements Serializable {
    /**
     * 库存 id
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '库存id'")
    private String inventoryId;
    /**
     * 类别编号
     */
    @Column(columnDefinition = "varchar(255) comment '类别编号'")
    private String categoryId;
    /**
     * 类别名称
     */
    @Column(columnDefinition = "varchar(255) comment '类别名称'")
    private String categoryName;
    /**
     * 数量
     */
    @Column(columnDefinition = "int(11) comment '数量'")
    private Integer counts;
    /**
     * 企业 id
     */
    @Column(columnDefinition = "varchar(255) comment '企业id'")
    private String enterpriseId;
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
