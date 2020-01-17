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

/**
 * @author: ddh
 * @data: 2020/1/14 16:29
 * @description
 **/
@Entity(name = "ss_plan")
@Table(appliesTo = "ss_plan", comment = "分拣规划")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Plan {
    /**
     * 规划 id
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '规划id'")
    private String planId;
    /**
     * 分拣口编号
     */
    @Column(columnDefinition = "varchar(255) comment '分拣口编号'")
    private String sortPortId;
    /**
     * 分类编号
     */
    @Column(columnDefinition = "varchar(255) comment '分类编号'")
    private String categoryId;
    /**
     * 计划分拣数量
     */
    @Column(columnDefinition = "int(11) comment '计划分拣数量'")
    private Integer planCounts;
    /**
     * 完成分拣数量
     */
    @Column(columnDefinition = "int(11) comment '完成分拣数量'")
    private Integer completeCounts;
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
     * 最后修改日期
     */
    @Column(columnDefinition = "bigint(20) comment '最后修改日期'")
    @LastModifiedDate
    private Long updateTime;
}
