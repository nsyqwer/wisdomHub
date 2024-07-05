package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生和活动关系表
 * @TableName student_activity
 */
@TableName(value ="student_activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentActivity implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动id
     */
    @TableField(value = "activity_id")
    private Integer activityId;

    /**
     * 学生id
     */
    @TableField(value = "student_id")
    private Integer studentId;

    /**
     * 签到时间
     */
    @TableField(value = "signin_time")
    private LocalDateTime signinTime;

    /**
     * 学生名字
     */
    @TableField(value = "student_name")
    private String studentName;

    /**
     * 签到状态（已签，缺勤，事假，病假，公假，迟到）
     */
    @TableField(value = "signin_status")
    private String signinStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}