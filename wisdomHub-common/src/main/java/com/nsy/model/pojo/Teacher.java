package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
public class Teacher implements Serializable {
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
     * 工号
     */
    @TableField(value = "tno")
    private String tno;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}