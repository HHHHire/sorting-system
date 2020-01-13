package cn.edu.jxust.sort.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: ddh
 * @data: 2020/1/3 10:10
 * @description
 **/
@Entity(name = "ss_binding")
@Table(appliesTo = "ss_binding", comment = "绑定信息表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Binding implements Serializable {
    @Id
    @Column(nullable = false, columnDefinition = "varchar(255) comment 'mq客户端ID'")
    private String clientId;
    @Column(nullable = false, columnDefinition = "varchar(255) comment 'mq ID'")
    private String mqId;
    @Column(nullable = false, columnDefinition = "varchar(255) comment 'mq access key'")
    private String accessKey;
    @Column(nullable = false, columnDefinition = "varchar(255) comment 'topic名称'")
    private String topic;
    @Column(nullable = false, columnDefinition = "varchar(255) comment '订阅名称'")
    private String subscribe;
    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) comment '设备ID'")
    private String deviceId;
    @Column(updatable = false, columnDefinition = "bigint(20) comment'创建时间'")
    @CreatedDate
    private Long createTime;
}
