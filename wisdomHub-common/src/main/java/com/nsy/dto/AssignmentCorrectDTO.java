package com.nsy.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @className: AssignmentCorrectDTO
 * @author: 宁舒意
 * @description: TODO批改作业接口DTO
 * @date: 2024/6/4 11:18
 */
@Data
public class AssignmentCorrectDTO {

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 作业id
     */
    private Integer assignmentId;
    /**
     * 老师id(负责批改的老师)
     */
    private Integer teacherId;

    /**
     * 评语
     */
    private String comment;

    /**
     * 学生回答内容（json）（AssignmentQuestion类的对象），此时每个content里面的每道题目要有分数和评语
     */
    private String content;

}
