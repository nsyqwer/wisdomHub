package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.nsy.model.pojo.Class;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @className: TeacherAssignVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/5 11:54
 */
@Data
public class TeacherAssignVO {

    /**
     * 作业id
     */
    private Integer assignmentId;

    /**
     * 作业标题
     */
    private String title;

    /**
     * 开始时间
     */
    private LocalDateTime beginDate;

    /**
     * 结束时间
     */
    private LocalDateTime endDate;

    /**
     * 作业状态，（未开始草稿0，进行中1，已结束2）
     */
    private Integer state;

    /**
     * 发放作业对象
    **/
    private List<Class> classList;

    /**
     * 待批阅人数
    **/
    private  Integer waitCorrectNum;
    /**
     * 已批阅人数
    **/
    private Integer finishedNum;

    /**
     * 总人数
    **/
    private Integer allNum;

    /**
     * 未提交人数
    **/
    private Integer unCommittedNum ;






}

