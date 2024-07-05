package com.nsy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Value;

/**
 * 
 * @TableName assignment
 */
@TableName(value ="assignment")
@Data
public class Assignment implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Integer courseId;

    /**
     * 课程名字
     */
    @TableField(value = "course_name")
    private String courseName;

    /**
     * 开始时间
     */
    @TableField(value = "begin_date")
    private LocalDateTime beginDate;

    /**
     * 结束时间
     */
    @TableField(value = "end_date")
    private LocalDateTime endDate;

    /**
     * 作业内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 作业总分
     */
    @TableField(value = "score")
    private BigDecimal score;

    /**
     * 作业标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 作业状态（草稿，进行中，已结束）
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 作业1，考试2
    **/
    @TableField(value = "type")
    private Integer type;

    /**
     * 考试时间，单位是分钟
    **/
    @TableField
    private Integer examTime;

    /**
     * class_list,发放作业的对象
    **/
    private String classList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}