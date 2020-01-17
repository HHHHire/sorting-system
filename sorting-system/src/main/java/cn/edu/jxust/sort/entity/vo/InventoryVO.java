package cn.edu.jxust.sort.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;

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
    private BigDecimal categoryLength;
    private BigDecimal lengthTolerancePo;
    private BigDecimal lengthToleranceNe;
    private BigDecimal weight;
    private BigDecimal weightTolerance;
    private Integer counts;
    private Long updateTime;
}
