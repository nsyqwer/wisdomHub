package com.nsy.model.vo;

import com.nsy.model.pojo.Student;
import com.nsy.model.pojo.StudentActivity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: SigninVo
 * @author: 文旅航
 * @description: 签到的返回前端数据
 * @date: 2024/5/16 20:33
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityVo {
    /**
     * 对于签到：学生考情情况集合
    **/
    List<StudentActivity> studentSigninList;

    /**
     * 图片（手势图片或智能考勤图片）
    **/
    String answer_image;

    /**
     * 检测后图片
    **/
    String detection_image;

    /**
     * 选人：选中的学生集合
    **/
    List<Student> students;
}
