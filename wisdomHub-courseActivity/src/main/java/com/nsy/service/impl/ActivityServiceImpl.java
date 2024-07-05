package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.mapper.StudentActivityMapper;
import com.nsy.mapper.StudentMapper;
import com.nsy.model.pojo.Activity;
import com.nsy.model.pojo.Student;
import com.nsy.model.pojo.StudentActivity;
import com.nsy.service.ActivityService;
import com.nsy.mapper.ActivityMapper;
import com.nsy.util.xunfei.face.WebFaceDetect;
import com.nsy.model.vo.ActivityTypeVo;
import com.nsy.model.vo.ActivityVo;
import com.nsy.model.vo.StudentSiginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ActivityTypeVo> getByAllType(Integer courseId, Integer classId) {
        return activityMapper.getByAllType(courseId, classId);
    }

    @Override
    public void add(Activity activity) throws Exception {
        if(activity.getActivityType() == 0) {
            List<Student> students = studentMapper.selectByClassId(activity.getClassId());
            System.out.println(students);
            if (activity.getType() == 0) { // 智能考勤
                List<Student> noReachStudents = WebFaceDetect.getNoReachStudents(students, activity.getAnswerImage());
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
            activityMapper.insert(activity);
        }
        else{
            //添加空集合进入选人活动中
            List<Student> students = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(students);
            activity.setStudents(s);
            activityMapper.insert(activity);
        }
    }

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

            ActivityVo activityVo = new ActivityVo(studentActivityList, activity.getAnswerImage(), activity.getDetectionImage(), null);
            return activityVo;
        }
        else{
            // 获取选人活动中的选的人的学生集合
            ObjectMapper mapper = new ObjectMapper();
            List<Student> students = mapper.readValue(activity.getStudents(), new TypeReference<List<Student>>(){});

            ActivityVo activityVo = new ActivityVo(null, activity.getAnswerImage(), activity.getDetectionImage(), students);
            return activityVo;
        }
    }

    @Override
    public StudentSiginVo getSiginVoById(Integer activityId, Integer studentId) {
        Activity activity = activityMapper.selectById(activityId);
        StudentActivity studentActivity = studentActivityMapper.select(activityId, studentId);
        StudentSiginVo studentSiginVo = new StudentSiginVo(activity.getType(), studentActivity.getSigninStatus(), studentActivity.getSigninTime());
        return studentSiginVo;
    }
}




