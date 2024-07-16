package com.nsy.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AddChooserDto implements Serializable {
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
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;
    /**
     * 选人（0：人脸选人，1：普通选人）
     */
    @TableField(value = "type")
    private Integer type;
    /**
     * 图片（人脸选人图片）
     */
    @TableField(value = "answer_image")
    private String answerImage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
