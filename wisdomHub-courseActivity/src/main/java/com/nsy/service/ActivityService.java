package com.nsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsy.model.dto.AddChooserDto;
import com.nsy.model.dto.AddSiginDto;
import com.nsy.model.pojo.Activity;
import com.nsy.model.vo.ActivityMessageVo;
import com.nsy.model.vo.ActivityVo;
import com.nsy.model.vo.StudentSiginVo;

import java.util.List;

/**
* @author 86155
* @description 针对表【activity(活动)】的数据库操作Service
* @createDate 2024-06-13 19:49:07
*/
public interface ActivityService extends IService<Activity> {

    void addChooser(Integer activityId, Integer studentId) throws JsonProcessingException;

    ActivityVo getActivityVoById(Integer activityId) throws JsonProcessingException;

    StudentSiginVo getSiginVoById(Integer activityId, Integer studentId);

    Activity addSigin(AddSiginDto sigin) throws Exception;

    Activity addChooser(AddChooserDto addChooser) throws JsonProcessingException;

    List<ActivityMessageVo> getByAllMessage(Integer courseId, Integer classId);
}
