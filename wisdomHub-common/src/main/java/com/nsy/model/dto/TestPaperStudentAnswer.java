package com.nsy.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TestPaperStudentAnswer {
    /**
     * 试卷id
    **/
    private Integer testId;
    /**
     * 姓名
     **/
    private String name;
    /**
     * 学号
     **/
    private String number;
    /**
     * 班级
     **/
    private String className;
    /**
     * 学生答案及评阅情况json数据
     **/
    private String content;
    /**
     * 对应学生试卷集合
    **/
    private List<String> testPaperImages;
    /**
     * 学生总分
     **/
    private BigDecimal studentScore;
}
