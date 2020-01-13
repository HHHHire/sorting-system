package cn.edu.jxust.sort.util.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ddh
 * @data: 2020/1/3 11:16
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> implements Response{
    private Integer code;
    private String msg;
    private T data;
}
