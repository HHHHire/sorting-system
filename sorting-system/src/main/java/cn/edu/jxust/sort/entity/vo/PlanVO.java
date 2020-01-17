package cn.edu.jxust.sort.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ddh
 * @data: 2020/1/16 20:51
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanVO {
    private String sortPortId;
    private String categoryId;
    private Integer planCounts;
    private Integer completeCounts;
    private Long createTime;
    private Long updateTime;
}
