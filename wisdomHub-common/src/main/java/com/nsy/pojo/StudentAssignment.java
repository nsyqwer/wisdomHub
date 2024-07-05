package com.nsy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    /**
     * 作业标题
    **/
    @TableField(value = "title")
    private String title;

    /**
     * 作业1，考试2
     **/
    @TableField(value = "type")
    private Integer type;

    /**
     * 学生开始考试时间
    **/
    @TableField(value = "exam_begin_time")
    private LocalDateTime examBeginTime;

    /**
     * 学生结束考试时间
    **/
    @TableField(value = "exam_end_time")
    private LocalDateTime examEndTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}