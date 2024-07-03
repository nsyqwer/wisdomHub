package com.nsy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName student_task_point
 */
@TableName(value ="student_task_point")
@Data
public class StudentTaskPoint implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     */
    @TableField(value = "student_id")
    private Integer studentId;

    /**
     * 章节任务点id
     */
    @TableField(value = "task_point_id")
    private Integer taskPointId;

    /**
     * 完成进度，100表示完成
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Integer courseId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}