package com.nsy.pojo;

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
 * @TableName student_exam
 */
@TableName(value ="student_exam")
@Data
public class StudentExam implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 评语
     */
    @TableField(value = "comment")
    private String comment;

    /**
     * 学生得分
     */
    @TableField(value = "student_score")
    private BigDecimal studentScore;

    /**
     * 老师id
     */
    @TableField(value = "teacher_id")
    private Integer teacherId;

    /**
     * 状态
     */
    @TableField(value = "state")
    private String state;

    /**
     * 学生开始考试时间
     */
    @TableField(value = "student_begin_date")
    private Date studentBeginDate;

    /**
     * 学生结束考试时间
     */
    @TableField(value = "student_end_date")
    private Date studentEndDate;

    /**
     * 考试标题
     */
    @TableField(value = "exam_title")
    private String examTitle;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Integer courseId;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}