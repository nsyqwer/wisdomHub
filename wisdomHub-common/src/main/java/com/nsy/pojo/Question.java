package com.nsy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题目类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 题干
     */
    @TableField(value = "title")
    private String title;

    /**
     * 答案
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * 答案解析
     */
    @TableField(value = "answer_analysis")
    private String answerAnalysis;

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
     * 创建者id
     */
    @TableField(value = "creator_id")
    private Integer creatorId;

    /**
     * 创建者名字
     */
    @TableField(value = "creator_name")
    private String creatorName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}