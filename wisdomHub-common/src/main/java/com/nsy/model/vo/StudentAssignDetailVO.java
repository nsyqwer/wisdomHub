package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.nsy.model.dto.AssignmentQuestionDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @className: StudentAssignDetailVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/7 11:06
 */
@Data
public class StudentAssignDetailVO {
    /**
     * 学生姓名
     */

    private String name;

    /**
     * 班级名称
     */
    private String className;


    private List<AssignmentQuestionDTO> questionList;

    /**
     * 作业标题
     */
    private String title;

    /**
     * 学生得分(作业总得分)
     */
    private BigDecimal studentScore;


}
