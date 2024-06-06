package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName student_signin
 */
@TableName(value ="student_signin")
@Data
public class StudentSignin implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 签到表id
     */
    @TableField(value = "signin_id")
    private Integer signinId;

    /**
     * 学生id
     */
    @TableField(value = "student_id")
    private Integer studentId;

    /**
     * 签到时间
     */
    @TableField(value = "signin_time")
    private Date signinTime;

    /**
     * 学生名字
     */
    @TableField(value = "student_name")
    private String studentName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}