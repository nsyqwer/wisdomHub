package com.nsy.model.dto;

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
     * 作业id(传了id就是编辑，没传就是新增)
    **/
    private Integer assignmentId;

    /**
     * 作业1，考试2
    **/
    private Integer type;


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
