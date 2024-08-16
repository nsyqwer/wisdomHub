package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 试卷信息
 * @TableName test_paper
 */
@TableName(value ="test_paper")
@Data
public class TestPaper implements Serializable {
    /**
     * 试卷id
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
     * 试卷名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 状态（0：草稿，1：完成）
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 题目图片地址json数据
     */
    @TableField(value = "questions_image")
    private String questionsImage;

    /**
     * 题目json数据
     */
    @TableField(value = "questions")
    private String questions;

    /**
     * 答案文档地址
     */
    @TableField(value = "answers_docx")
    private String answersDocx;

    /**
     * 答案json数据
     */
    @TableField(value = "answers")
    private String answers;

    /**
     * 发布班级集合
     */
    @TableField(value = "class_list")
    private String classList;

    /**
     * 总人数
     */
    @TableField(value = "number")
    private Integer number;

    /**
     * 完成时间
     */
    @TableField(value = "time")
    private LocalDateTime time;

    /**
     * 试卷总分
     */
    @TableField(value = "score")
    private Integer score;

    /**
     * 老师id
    **/
    @TableField(value = "teacher_id")
    private Integer teacherId;

    /**
     * 老师名字
    **/
    @TableField(value = "teacher_name")
    private String teacherName;

    /**
     * 所有学生试卷集合
    **/
    @TableField(value = "test_paper_images")
    private String testPaperImages;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}