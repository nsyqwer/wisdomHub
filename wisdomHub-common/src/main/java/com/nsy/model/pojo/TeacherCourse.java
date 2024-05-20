package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName teacher_course
 */
@TableName(value ="teacher_course")
@Data
public class TeacherCourse implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 教师id
     */
    @TableField(value = "teacher_id")
    private Integer teacherId;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Integer courseId;

    /**
     * 课程名字
     */
    @TableField(value = "course_name")
    private String courseName;

    /**
     * 课程封面
     */
    @TableField(value = "course_image")
    private String courseImage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}