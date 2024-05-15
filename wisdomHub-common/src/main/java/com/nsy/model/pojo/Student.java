package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName student
 */
@TableName(value ="student")
@Data
public class Student implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学校id
     */
    @TableField(value = "school_id")
    private Integer schoolId;

    /**
     * 学号
     */
    @TableField(value = "sno")
    private String sno;

    /**
     * 班级id
     */
    @TableField(value = "class_id")
    private Integer classId;

    /**
     * 班级名称
     */
    @TableField(value = "class_name")
    private String className;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}