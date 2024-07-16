package com.nsy.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class StudentTestPaperInfo {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 姓名
    **/
    private String name;
    /**
     * 学号
    **/
    private String number;
    /**
     * 班级
    **/
    private String className;
    /**
     * 学生答案及评阅情况json数据
    **/
    private String content;
    /**
     * 学生总分
    **/
    private Integer studentScore;
}
