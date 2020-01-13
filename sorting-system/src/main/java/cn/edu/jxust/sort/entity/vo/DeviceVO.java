package cn.edu.jxust.sort.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: ddh
 * @data: 2020/1/7 10:48
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceVO {
    @NotBlank
    private String deviceId;

    @NotBlank
    private String deviceName;

    private Long createTime;
}
