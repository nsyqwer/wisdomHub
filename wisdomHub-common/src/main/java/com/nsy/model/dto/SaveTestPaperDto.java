package com.nsy.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * 保存草稿
**/
@Data
public class SaveTestPaperDto {
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
     * 老师id
    **/
    @TableField(value = "teacher_id")
    private Integer teacherId;
    /**
     * 试卷名称
     */
    @TableField(value = "title")
    private String title;
    /**
     * 题目图片地址
     */
    @TableField(value = "questions_image")
    private String questionsImage;

    /**
     * 题目json数据
     */
    @TableField(value = "questions")
    private Integer questions;
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
     * 试卷总分
     */
    @TableField(value = "score")
    private Integer score;
    /**
     * 学生考试信息集合
    **/
    private List<StudentTestPaperInfo> studentTestPapers;
}
