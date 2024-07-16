package com.nsy.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TestStudentAnswerVo {
    /**
     * 学生姓名
    **/
    private String name;
    /**
     * 学生班级
    **/
    private String className;
    /**
     * 学生学号
    **/
    private String sno;
    /**
     * 试卷名称
    **/
    private String title;
    /**
     * 学生回答内容及评阅
    **/
    private String content;
    /**
     * 学生得分
    **/
    private BigDecimal studentScore;
}
