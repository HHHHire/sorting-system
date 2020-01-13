package cn.edu.jxust.sort.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ddh
 * @data: 2020/1/8 19:54
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordVO {
    private String recordId;
    private String categoryId;
    private String categoryName;
    private Integer counts;
    private Long createTime;
}
