package com.nsy.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TestPaperAnswer {
    /**
     * 试卷id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
}
