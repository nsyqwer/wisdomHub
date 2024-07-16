package com.nsy.model.vo;

import com.nsy.model.pojo.StudentActivity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddActivityVo {
    /**
     * 签到id
    **/
    private Integer id;
    /**
     * 签到后的检测图片
    **/
    private String detectionImage;
    /**
     * 学生签到情况集合
    **/
    private List<StudentActivity> studentActivities;
}
