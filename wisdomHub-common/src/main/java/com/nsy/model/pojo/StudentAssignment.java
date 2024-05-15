package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName student_assignment
 */
@TableName(value ="student_assignment")
@Data
public class StudentAssignment implements Serializable {
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
     * 作业id
     */
    @TableField(value = "assignment_id")
    private Integer assignmentId;

    /**
     * 作业完成状态
     */
    @TableField(value = "state")
    private String state;

    /**
     * 学生得分
     */
    @TableField(value = "student_score")
    private BigDecimal studentScore;

    /**
     * 老师id(负责批改的老师)
     */
    @TableField(value = "teacher_id")
    private Integer teacherId;

    /**
     * 评语
     */
    @TableField(value = "comment")
    private String comment;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}