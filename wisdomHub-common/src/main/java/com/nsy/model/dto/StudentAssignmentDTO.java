package com.nsy.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @className: StudentAssignmentDTO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/16 21:05
 */

@Data
public class StudentAssignmentDTO {
    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 作业id
     */
    private Integer assignmentId;

    /**
     * 作业完成状态
     */
    private Integer state;

    /**
     * 学生作答内容（json）
     */
    private String content;
}
