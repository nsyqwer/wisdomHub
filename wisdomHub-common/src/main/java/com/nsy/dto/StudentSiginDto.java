package com.nsy.dto;

import lombok.Data;

/**
 * 学生进行签到请求类
 * @author 文旅航
 * @date 2024/7/3 10:24
 * @className StudentSiginDto
**/
@Data
public class StudentSiginDto {
    /**
     * 活动id
    **/
    Integer activityId;
    /**
     * 学生id
    **/
    Integer studentId;
    /**
     * 签到（0：智能考勤，1：普通签到，2：手势签到，3：签到码签到）
    **/
    Integer type;
    /**
     * 答案手势或签到码
    **/
    String answer;
    /**
     * 手势图片
    **/
    String answerImage;
}
