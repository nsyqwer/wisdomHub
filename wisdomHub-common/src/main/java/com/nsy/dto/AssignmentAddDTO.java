package com.nsy.dto;

import lombok.Data;

import java.util.List;

/**
 * @className: AssignmentAddDTO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/6/3 21:42
 */
@Data
public class AssignmentAddDTO {

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 作业标题
     */
    private String title;

    /**
     * 作业内容
     */
    private List<AssignmentQuestionDTO> content;
}
