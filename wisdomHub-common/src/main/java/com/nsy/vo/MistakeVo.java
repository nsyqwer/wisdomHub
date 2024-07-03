package com.nsy.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @className: MistakeVo
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/17 11:29
 */
@Data
public class MistakeVo {
    /**
     * 题目id
     */
    private Integer questionId;

    /**
     * 课程名字
     */
    private String courseName;

    /**
     * 题目类型
     */
    private String type;

    /**
     * 题干
     */
    private String title;
}
