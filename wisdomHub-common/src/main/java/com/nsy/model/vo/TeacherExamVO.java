package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @className: TeacherExamVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/8 23:31
 */
@Data
public class TeacherExamVO {


    /**
     * 考试id
    **/
    private Integer examId;
    /**
     * 课程id
    **/
    private Integer courseId;
    /**
     * 课程名字
     */
    private String courseName;

    /**
     * 考试标题
     */

    private String title;

    /**
     * 作业状态，（未开始草稿0，进行中1，已结束2）
     */
    private String state;

    /**
     * 题量
    **/
    private Integer questionNum;

    /**
     * 创建人名字
    **/
    private String creatorName;




}
