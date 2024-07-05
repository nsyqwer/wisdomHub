package com.nsy.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @className: StudyRecordVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/17 10:27
 */
@Data
public class StudyRecordVO {

    /**
     * 该课程所有任务点数量
     */
    private int allTaskPointNum;
    /**
     * 已完成任务点数量
     */
    private int finishTaskPointNum;

    /**
     * 完成任务点数量在班级排名
     */
    private int classRank;

    /**
     * 该课程总作业数量
     */
    private int allAssignmentNum;

    /**
     * 该课程已完成作业数量
     */
    private int finishAssignmentNum;

    /**
     * 该课程作业平均分
     */
    private BigDecimal avgAssignmentScore;

    /**
     * 该课程总考试数量
     */
    private int allExamNum;

    /**
     * 该课程已完成考试数量
     */
    private int finishExamNum;

    /**
     * 该课程平均考试分数
     */
    private BigDecimal avgExamScore;

    /**
     * 历次考试分数
     */
    List<ExamHistoryVo> examHistoryVoList;


}
