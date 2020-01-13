package cn.edu.jxust.sort.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: ddh
 * @data: 2020/1/8 16:26
 * @description
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    private String categoryId;
    private String categoryName;
    private BigDecimal length;
    private BigDecimal lengthTolerance;
    private BigDecimal weight;
    private BigDecimal weightTolerance;
}
