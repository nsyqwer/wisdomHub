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
 * @TableName signin
 */
@TableName(value ="signin")
@Data
public class Signin implements Serializable {
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
     * 老师id
     */
    @TableField(value = "teacher_id")
    private Integer teacherId;

    /**
     * 签到标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 开始时间
     */
    @TableField(value = "begin_time")
    private Date beginTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 手势还是二维码
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 答案手势
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * 老师名字
     */
    @TableField(value = "teacher_name")
    private String teacherName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}