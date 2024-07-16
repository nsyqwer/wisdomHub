package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *  存储活动的基础信息
 * @author 文旅航
 * @date 2024/7/6 13:55
 * @className ActivityMessageVo
**/
@Data
public class ActivityMessageVo {
    /**
     * 活动id
     **/
    Integer id;
    /**
     * 活动类型（0：签到，1：选人)
     **/
    Integer activityType;
    /**
     * 活动名称
     */
    @TableField(value = "title")
    private String title;
    /**
     * 开始时间
     */
    @TableField(value = "begin_time")
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;
}
