package com.nsy.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
     * 保存（1）还是提交（2），如果是考试，则只能进行提交operation=2
    **/
    private Integer operation;

    /**
     * 学生作答内容（json）
     */
    private List<AssignmentQuestionDTO> content;

    /**
     * type(1作业，2考试)
    **/
    private Integer type;

    /**
     * (考试才需要这个字段)学生开始考试时间
     **/
    private LocalDateTime examBeginTime;

    /**
     * (考试才需要这个字段)学生结束考试时间
     **/
    private LocalDateTime examEndTime;
}
