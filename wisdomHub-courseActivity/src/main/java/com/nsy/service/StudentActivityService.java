package com.nsy.service;

import com.nsy.dto.UpdateSiginDto;
import com.nsy.pojo.StudentActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86155
* @description 针对表【student_activity(学生和活动关系表)】的数据库操作Service
* @createDate 2024-06-13 20:44:00
*/

public interface StudentActivityService extends IService<StudentActivity> {
    void updateSigin(UpdateSiginDto updateSiginDto);

    List<StudentActivity> getByActivityId(Integer activityId);
}
