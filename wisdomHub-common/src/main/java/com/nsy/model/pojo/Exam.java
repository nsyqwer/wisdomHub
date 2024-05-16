package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName exam
 */
@TableName(value ="exam")
@Data
public class Exam implements Serializable {
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
     * 开始时间
     */
    @TableField(value = "begin_date")
    private Date beginDate;

    /**
     * 结束时间
     */
    @TableField(value = "end_date")
    private Date endDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}