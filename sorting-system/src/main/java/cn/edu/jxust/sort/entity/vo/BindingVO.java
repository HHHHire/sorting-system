package cn.edu.jxust.sort.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: ddh
 * @data: 2020/1/12 21:37
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BindingVO {

    @NotNull
    private String mqId;

    @NotNull
    private String accessKey;

    @NotNull
    private String topic;

    @NotNull
    private String subscribe;

    @NotNull
    private String deviceId;
}
