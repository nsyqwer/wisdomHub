package com.nsy.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SaveQuestionInfo {
    /**
     * 试卷id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 试卷名称
     */
    @TableField(value = "title")
    private String title;
    /**
     * 题目图片地址(json数据)
     */
    @TableField(value = "questions_image")
    private String questionsImage;

    /**
     * 题目json数据
     */
    @TableField(value = "questions")
    private String questions;
    /**
     * 试卷总分
     */
    @TableField(value = "score")
    private Integer score;
}
