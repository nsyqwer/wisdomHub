package com.nsy.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @className: ExamHistoryVo
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/17 10:52
 */
@Data
public class ExamHistoryVo {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 考试标题
     */
    private String examTitle;

    /**
     * 学生得分
     */
    private BigDecimal studentScore;
}
