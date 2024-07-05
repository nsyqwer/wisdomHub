package com.nsy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @className: AssignmentPublishDTO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/6/3 21:57
 */
@Data
public class AssignmentPublishDTO {

    /**
     * 作业1，考试2
     **/
    private Integer type;

    /**
     * 作业id
     */
    private Integer assignmentId;
    /**
     * 开始时间
     */
    private LocalDateTime beginDate;

    /**
     * 结束时间
     */
    private LocalDateTime endDate;

    /**
     * 考试时间
    **/
    private Integer examTime;
    /**
     * 作业发放对象，班级ID集合
     */
    List<Integer> classIdList;

}
