package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName mistake
 */
@TableName(value ="mistake")
@Data
public class Mistake implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题目id
     */
    @TableField(value = "question_id")
    private Integer questionId;

    /**
     * 学生id
     */
    @TableField(value = "student_id")
    private Integer studentId;

    /**
     * 学生作答
     */
    @TableField(value = "student_answer")
    private String studentAnswer;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}