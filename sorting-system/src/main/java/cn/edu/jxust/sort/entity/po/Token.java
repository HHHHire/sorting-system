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
@Entity(name = "ss_token")
@Table(appliesTo = "ss_token", comment = "token表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {
    /**
     * 用户 ID
     */
    @Id
    @Column(columnDefinition = "varchar(255) comment '用户id'")
    private String userId;
    /**
     * 用户 Token，非空且唯一
     */
    @Column(unique = true, nullable = false, columnDefinition = "varchar(300) comment '用户 Token'")
    private String userToken;
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
