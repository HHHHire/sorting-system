package cn.edu.jxust.sort.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ddh
 * @data: 2020/1/9 10:35
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryVO {
    private String categoryName;
    private Integer counts;
    private Long updateTime;
}
