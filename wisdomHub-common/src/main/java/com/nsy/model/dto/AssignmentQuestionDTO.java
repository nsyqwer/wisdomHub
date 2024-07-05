package com.nsy.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @className: AssignmentQuestionDTO
 * @author: 宁舒意
 * @description: 作业里面的每道题目
 * @date: 2024/6/3 19:49
 */
@Data
public class AssignmentQuestionDTO {
    //题目分数
    private BigDecimal questionScore;
    //学生得分数
    private BigDecimal studentScore;
    //题目类型
    private String type;
    //题干
    private String title;
    //学生作答
    private String studentAnswer;
    //题目评语
    private String questionComment;
    //答案
    private String answer;
    //解析
    private String answerAnalysis;

}
