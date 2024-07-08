package com.nsy.model.dto;

import lombok.Data;

/**
 * @className: TeacherAssignmentDTO
 * @author: 宁舒意
 * @description: 教师：查看课程的作业的查询类
 * @date: 2024/7/5 16:02
 */
@Data
public class TeacherAssignmentDTO {
    /**
     * 课程id
    **/
    private Integer courseId;

    /**
     * 作业或者考试状态(草稿0，进行中1，已结束2)
    **/
    private Integer state;
    /**
     * 作业1，考试2
    **/
    private Integer type;

}
