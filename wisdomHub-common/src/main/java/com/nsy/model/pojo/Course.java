package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.Value;

/**
 * 
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名字
     */
    @TableField(value = "course_name")
    private String courseName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

//    /**
//     * 创建者id
//     */
//    @TableField(value = "user_id")
//    private Integer userId;

    /**
     * 封面
     */
    @TableField(value = "image")
    private String image;

    /**
     * markdown格式的思维导图
    **/
    @TableField(value="mind_map")
    private String mindMap;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}