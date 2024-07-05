package com.nsy.model.vo;

import lombok.Data;

/**
 * 存储活动类型
 * @author 文旅航
 * @date 2024/7/2 17:17
 * @className ActivityTypeVo
**/

@Data
public class ActivityTypeVo {
    /**
     * 活动id
    **/
    Integer id;
    /**
     * 活动类型（0：签到，1：选人)
    **/
    Integer activityType;
}
