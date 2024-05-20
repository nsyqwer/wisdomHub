package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @className: MistakeDetailVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/17 11:35
 */
@Data
public class MistakeDetailVO {
    /**
     * 题目类型
     */
    private String type;

    /**
     * 题干
     */
    private String title;

    /**
     * 答案
     */
    private String answer;

    /**
     * 答案解析
     */
    private String answerAnalysis;

    /**
     * 学生作答
     */
    private String studentAnswer;

    /**
     * 课程名字
     */
    private String courseName;

}
