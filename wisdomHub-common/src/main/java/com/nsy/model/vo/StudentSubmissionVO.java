package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @className: StudentSubmissionVO
 * @author: 宁舒意
 * @description: 教师查看学生提交作业考生列表
 * @date: 2024/7/7 10:14
 */
@Data
public class StudentSubmissionVO {


    /**
     * 学生作答作业考试id，用来查询考生作答详情
    **/
    private Integer studentAssignmentId;


    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学号
     */
    private String sno;


    /**
     * 作业考试完成状态（未提交0，待批阅1，已完成2）
    **/
    private Integer state;

    /**
     * 学生得分(作业总得分)
     */
    private BigDecimal studentScore;

    /**
     * 开始时间
     */
    private LocalDateTime beginDate;

    /**
     * 结束时间
     */
    private LocalDateTime endDate;
}
