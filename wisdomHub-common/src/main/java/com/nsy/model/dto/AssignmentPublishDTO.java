package com.nsy.model.dto;

import lombok.Data;

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
     * 作业id
     */
    private Integer assignmentId;
    /**
     * 开始时间
     */
    private Date beginDate;

    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 作业发放对象，班级ID集合
     */
    List<Integer> classIdList;

}
