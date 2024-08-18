package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.model.dto.AddChooserDto;
import com.nsy.model.dto.AddSiginDto;
import com.nsy.mapper.ActivityMapper;
import com.nsy.mapper.StudentActivityMapper;
import com.nsy.mapper.StudentMapper;
import com.nsy.mapper.mapstruct.ActivityDtoMapstruct;
import com.nsy.model.pojo.Activity;
import com.nsy.model.pojo.Student;
import com.nsy.model.pojo.StudentActivity;
import com.nsy.service.ActivityService;
import com.nsy.util.xunfei.face.WebFaceDetect;
import com.nsy.model.vo.ActivityMessageVo;
import com.nsy.model.vo.ActivityVo;
import com.nsy.model.vo.StudentSiginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
* @author 86155
* @description 针对表【activity(活动)】的数据库操作Service实现
* @createDate 2024-06-13 19:49:07
*/
@Service
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService{

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StudentActivityMapper studentActivityMapper;

    @Autowired
    ActivityDtoMapstruct activityDtoMapstruct;

    @Override
    public void addChooser(Integer activityId, Integer studentId) throws JsonProcessingException {
        Activity activity = activityMapper.selectById(activityId);
        if(activity.getActivityType() == 0){
            log.error("非选人活动，无法进行选人");
            return ;
        }
        // ObjectMapper实例
        ObjectMapper mapper = new ObjectMapper();
        List<Student> students = mapper.readValue(activity.getStudents(), new TypeReference<List<Student>>(){});

        //进行新增选人活动的学生
        students.add(studentMapper.selectById(studentId));
        activity.setStudents(mapper.writeValueAsString(students));

        activityMapper.updateById(activity);
    }

    @Override
    public ActivityVo getActivityVoById(Integer activityId) throws JsonProcessingException {
        Activity activity = activityMapper.selectById(activityId);

        if(activity.getActivityType() == 0){
            List<StudentActivity> studentActivityList = studentActivityMapper.selectByActivityId(activityId);

            return new ActivityVo(activity.getActivityType(), activity.getType(), studentActivityList, activity.getAnswerImage(), activity.getDetectionImage(), null);
        }
        else{
            // 获取选人活动中的选的人的学生集合
            ObjectMapper mapper = new ObjectMapper();
            List<Student> students = mapper.readValue(activity.getStudents(), new TypeReference<List<Student>>(){});

            return new ActivityVo(activity.getActivityType(), activity.getType(),null, activity.getAnswerImage(), activity.getDetectionImage(), students);
        }
    }

    @Override
    public StudentSiginVo getSiginVoById(Integer activityId, Integer studentId) {
        Activity activity = activityMapper.selectById(activityId);
        StudentActivity studentActivity = studentActivityMapper.select(studentId, activityId);

        LocalDateTime endTime = activity.getEndTime();

        int state = 0;
        if(endTime != null && endTime.isBefore(LocalDateTime.now())){
            state = 1;
        }

        return new StudentSiginVo(activity.getType(), studentActivity.getSigninStatus(), state,activity.getBeginTime(), activity.getEndTime(), studentActivity.getSigninTime());
    }

    /**
     * 添加签到活动
    **/
    @Override
    public Activity addSigin(AddSiginDto sigin) throws Exception {
        //获取activity并进行添加
        Activity activity = activityDtoMapstruct.siginDtoToActivity(sigin);

        System.out.println("活动：" + activity);
        activity.setActivityType(0);
        activityMapper.insert(activity);

        List<Student> students = studentMapper.selectByClassId(activity.getClassId());
        System.out.println(students);
        if (activity.getType() == 0) { // 智能考勤
            activity.setBeginTime(LocalDateTime.now());
            activity.setEndTime(LocalDateTime.now());

            List<Student> noReachStudents = WebFaceDetect.getNoReachStudents(students, activity.getAnswerImage(), activity.getClassId());
            System.out.println("检测后的图片：" + WebFaceDetect.detection_image);
            activity.setDetectionImage(WebFaceDetect.detection_image);

            students.removeAll(noReachStudents);

            System.out.println("未到学生：" + noReachStudents);
            System.out.println("已到达学生：" + students);

            for (Student student : students) {
                StudentActivity studentActivity = new StudentActivity(null, activity.getId(), student.getId(), activity.getBeginTime(), student.getName(), "已签");
                studentActivityMapper.insert(studentActivity);
            }
            for(Student student : noReachStudents){
                StudentActivity studentActivity = new StudentActivity(null, activity.getId(), student.getId(), activity.getBeginTime(), student.getName(), "缺勤");
                studentActivityMapper.insert(studentActivity);
            }
            //将之后存储进入的检测后图片进行存储。
            activityMapper.updateById(activity);
        }
        else{
            //对所有学生添加该签到活动
            log.info("添加签到活动");
            for(Student student : students){
                StudentActivity studentActivity = new StudentActivity(null, activity.getId(), student.getId(), activity.getBeginTime(), student.getName(), "");
                System.out.println(studentActivity);
                studentActivityMapper.insert(studentActivity);
            }
        }
        System.out.println(activity);
        return activity;
    }

    /**
     * 添加选人活动
    **/
    @Override
    public Activity addChooser(AddChooserDto chooser) throws JsonProcessingException {
        Activity activity = activityDtoMapstruct.addChooserToActivity(chooser);
        //存储活动类别
        activity.setActivityType(1);

        //添加空集合进入选人活动中
        List<Student> students = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(students);
        activity.setStudents(s);
        activityMapper.insert(activity);
        return activity;
    }

    @Override
    public List<ActivityMessageVo> getByAllMessage(Integer courseId, Integer classId) {
        return activityMapper.getAllMessage(courseId, classId);
    }
}




