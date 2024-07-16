package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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
     * 活动类别（0：签到，1：选人）
    **/
    Integer ActivityType;
    /**
     * 签到（0：智能考勤，1：普通签到，2：手势签到，3：签到码签到），选人（0：智能，1：普通）
     */
    @TableField(value = "type")
    private Integer type;
    /**
     * 对于签到：学生考情情况集合
    **/
    List<StudentActivity> studentSigninList;

    /**
     * 图片（手势图片或智能考勤图片）
    **/
    String answerImage;

    /**
     * 检测后图片
    **/
    String detectionImage;

    /**
     * 选人：选中的学生集合
    **/
    List<Student> students;
}
