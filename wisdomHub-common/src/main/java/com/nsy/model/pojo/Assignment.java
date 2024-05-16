package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

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
    private Date beginDate;

    /**
     * 结束时间
     */
    @TableField(value = "end_date")
    private Date endDate;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}