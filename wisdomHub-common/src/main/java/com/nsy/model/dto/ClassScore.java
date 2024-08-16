package com.nsy.model.dto;

import lombok.Data;

@Data
public class ClassScore {


    //班级id
    private Integer classId;

    //班级名称
    private String className;

    //平均分
    private float avgScore;

    //最小分值
    private float minScore;

    //最大分值
    private float maxScore;

    /**
     * 及格人数
    **/
    private Integer passingStudentsCount;
}