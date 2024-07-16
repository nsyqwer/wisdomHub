package com.nsy.model.vo;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nsy.model.dto.AssignmentQuestionDTO;
import lombok.Data;

/**
 * @className: CorrectQuestionVO
 * @author: 宁舒意
 * @description: 在Ai进行智能阅卷并且教师进行二次评阅后，对这场考试的前3道错得最多的题
 * @date: 2024/7/11 23:07
 */
@Data
public class CorrectQuestionVO {
    /**
     * 错题
    **/
    private AssignmentQuestionDTO mistakeQuestion;

    /**
     * 错题包含的知识点
    **/
    private String knowledgePoint;

    /**
     * 根据薄弱项推荐错题
    **/
    private AssignmentQuestionDTO answerQuestion;
}
