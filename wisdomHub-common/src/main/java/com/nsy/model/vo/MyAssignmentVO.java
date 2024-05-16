package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @className: MyAssignmentVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/16 20:33
 */
@Data
public class MyAssignmentVO {
    /**
     * 主键id
     */
    private Integer id;


    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 作业标题
     */
    private String title;

    /**
     * 作业状态
     */
    private String state;



}
