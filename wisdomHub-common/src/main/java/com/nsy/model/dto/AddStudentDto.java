package com.nsy.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddStudentDto implements Serializable {
    /**
     * 学生姓名
     **/
    @TableField(value = "name")
    private String name;

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
     * 人脸图片
     */
    @TableField(value = "face_image")
    private String faceImage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
