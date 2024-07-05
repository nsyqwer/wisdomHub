package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 活动
 * @TableName activity
 */
@TableName(value ="activity")
@Data
public class Activity implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Integer courseId;

    /**
     * 班级id
     */
    @TableField(value = "class_id")
    private Integer classId;

    /**
     * 老师id
     */
    @TableField(value = "teacher_id")
    private Integer teacherId;

    /**
     * 活动名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 开始时间
     */
    @TableField(value = "begin_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 签到（0：智能考勤，1：普通签到，2：手势签到，3：签到码签到）
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 答案手势或签到码
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * 图片（手势图片或智能考勤图片）
     */
    @TableField(value = "answer_image")
    private String answerImage;

    /**
     * 检测后的图片
     */
    @TableField(value = "detection_image")
    private String detectionImage;

    /**
     * 活动类别（0：签到，1：选人）
     */
    @TableField(value = "activity_type")
    private Integer activityType;

    /**
     * 选到的人id的集合,新建选人活动时为空
     */
    @TableField(value = "students")
    private String students;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}