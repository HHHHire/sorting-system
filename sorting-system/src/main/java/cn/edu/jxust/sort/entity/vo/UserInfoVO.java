package cn.edu.jxust.sort.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ddh
 * @data: 2020/1/6 9:09
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    private String userId;
    private String userName;
    private String userToken;
    private String userPhone;
    private String userEmail;
    private String enterpriseName;
    private Long createTime;
    private Long updateTime;
}
