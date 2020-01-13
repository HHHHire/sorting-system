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
@Entity(name = "ss_device")
@Table(appliesTo = "ss_device", comment = "设备表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device implements Serializable {
    /**
     * 设备编码
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '设备编码'")
    private String deviceId;
    /**
     * 设备名称
     */
    @Column(nullable = false, columnDefinition = "varchar(255) comment '设备名称'")
    private String deviceName;
    /**
     * 所属企业id
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
