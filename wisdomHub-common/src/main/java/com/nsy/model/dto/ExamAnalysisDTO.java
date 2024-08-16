package com.nsy.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @className: ExamDTO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/3/7 21:25
 */

@Data
public class ExamAnalysisDTO {

    //考试id
    private int examId;


    //考试名称
    private String title;


    //
    List<ClassScore> classScoreList;



}
