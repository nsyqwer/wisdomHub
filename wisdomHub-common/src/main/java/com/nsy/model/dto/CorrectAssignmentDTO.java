package com.nsy.model.dto;

import lombok.Data;

/**
 * @className: CorrectAssignmentDTO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/11 18:50
 */
@Data
public class CorrectAssignmentDTO {
    /**
     * 老师id
    **/
    private Integer teacherId;

    /**
     * 作业id
    **/
    private Integer assignmentId;

    /**
     * 班级id
    **/
    private Integer classId;
}
