package cn.edu.jxust.sort.entity.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: ddh
 * @data: 2020/1/8 14:59
 * @description
 **/
@Entity(name = "ss_record")
@Table(appliesTo = "ss_record", comment = "分拣记录表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Record implements Serializable {
    /**
     * 记录 id
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '记录id'")
    private String recordId;
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
     * 设备 id
     */
    @Column(columnDefinition = "varchar(255) comment '设备id'")
    private String deviceId;
    /**
     * 员工卡号
     */
    @Column(columnDefinition = "varchar(255) comment '员工卡号'")
    private String employeeCard;
    /**
     * 创建日期
     */
    @Column(columnDefinition = "bigint(20) comment '创建日期'")
    @CreatedDate
    private Long createTime;
}
