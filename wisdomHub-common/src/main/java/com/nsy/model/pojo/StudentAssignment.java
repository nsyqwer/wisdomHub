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
    private Integer state;

    /**
     * 学生得分(作业总得分)
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

    /**
     * 学生回答内容（json）（AssignmentQuestion类的对象）
     */
    @TableField(value = "content")
    private String content;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Integer courseId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}