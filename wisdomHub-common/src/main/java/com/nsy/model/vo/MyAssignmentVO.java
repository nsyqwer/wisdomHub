package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @className: MyAssignmentVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/16 20:33
 */
@Data
public class MyAssignmentVO {
    /**
     * 作业id
     */
    private Integer assignmentId;


    /**
     * 结束时间
     */
    private LocalDateTime endDate;

    /**
     * 作业标题
     */
    private String title;

    /**
     * 作业状态，已完成，未完成，待批阅
     */
    private String state;

    /**
     * 如果是考试的话，还需要考试时间
    **/
    private Integer examTime;



}
