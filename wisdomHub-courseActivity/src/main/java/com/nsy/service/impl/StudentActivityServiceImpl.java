package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.model.dto.UpdateSiginDto;
import com.nsy.mapper.StudentActivityMapper;
import com.nsy.model.pojo.StudentActivity;
import com.nsy.service.StudentActivityService;
import com.nsy.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author 86155
* @description 针对表【student_activity(学生和活动关系表)】的数据库操作Service实现
* @createDate 2024-06-13 20:44:00
*/
@Service
public class StudentActivityServiceImpl extends ServiceImpl<StudentActivityMapper, StudentActivity>
    implements StudentActivityService{
    @Autowired
    StudentActivityMapper studentActivityMapper;

    @Override
    public void updateSigin(UpdateSiginDto updateSiginDto) {
        StudentActivity studentActivity = studentActivityMapper.select(updateSiginDto.getStudentId(), updateSiginDto.getSiginId());
        if(studentActivity != null) {
            String newTime = Time.getNewTime();
            System.out.println(newTime);
            studentActivityMapper.update(updateSiginDto.getStudentId(), updateSiginDto.getSiginId(), updateSiginDto.getSigninStatus(),LocalDateTime.now());
        }
    }

    @Override
    public List<StudentActivity> getByActivityId(Integer activityId) {

        List<StudentActivity> studentSigninList = studentActivityMapper.selectByActivityId(activityId);
        return studentSigninList;
    }
}




