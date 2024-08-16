package com.nsy.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class TestPaperImageDto {
    /**
     * 试卷id
    **/
    private Integer testId;
    /**
     * 学生试卷
    **/
    private List<String> images;
}
