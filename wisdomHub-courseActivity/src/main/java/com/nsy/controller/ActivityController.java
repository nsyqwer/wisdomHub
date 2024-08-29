package com.nsy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsy.model.BaseResult;
import com.nsy.model.dto.AddChooserDto;
import com.nsy.model.dto.AddSiginDto;
import com.nsy.model.dto.StudentSiginDto;
import com.nsy.model.dto.UpdateSiginDto;
import com.nsy.model.pojo.Activity;
import com.nsy.model.pojo.Student;
import com.nsy.model.pojo.StudentActivity;
import com.nsy.service.ActivityService;
import com.nsy.service.StudentActivityService;
import com.nsy.service.StudentService;
import com.nsy.util.OSSUtils;
import com.nsy.util.xunfei.example.BigModelNew;
import com.nsy.util.xunfei.example.Main;
import com.nsy.util.xunfei.face.WebFaceBaidu;
import com.nsy.util.xunfei.face.WebFaceDetect;
import com.nsy.model.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentActivityService studentActivityService;

    /**
     * 教师：创建签到活动
     * @author 文旅航
     * @date 2024/7/12 16:30
     * @param sigin
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.AddActivityVo>
    **/

    @PutMapping("/addSigin")
    public BaseResult<AddActivityVo> addActivity(@RequestBody AddSiginDto sigin) throws Exception {
        System.out.println("智能考勤图片链接" + sigin.getAnswerImage());
        log.info("创建签到活动");

        long start = System.nanoTime();
        Activity activity = activityService.addSigin(sigin);
        long end = System.nanoTime();

        System.out.println("智能签到时间：" + ((end - start) / 1_000_000_000.0));

        List<StudentActivity> studentActivities = studentActivityService.getByActivityId(activity.getId());
        AddActivityVo activityVo = new AddActivityVo(activity.getId(), activity.getDetectionImage(), studentActivities);

        return new BaseResult<>(200,"创建签到活动成功", activityVo);
    }

    /**
     * 测试接口
     * @author 文旅航
     * @date 2024/8/8 19:07
     * @param classId
     * @return com.nsy.model.BaseResult
    **/

    @PostMapping("test")
    public BaseResult test(Integer classId) throws InterruptedException {
        log.info("进入测试方法");
        try {
            WebFaceBaidu.addGroup("" + classId);
        }
        catch (Exception e){
            System.out.println(MessageFormat.format("classId:{0}, 创建失败，可能该组已经存在", classId));
            return new BaseResult(400, MessageFormat.format("创建组(classId:{0}) 失败", classId));
        }
        List<Student> students = studentService.getByClassId(classId);

        log.info("进行人脸注册");
        System.out.println("该班总人数：" + students.size());
        for(Student student : students){
            System.out.println(student);
            try {
                WebFaceBaidu.addUser(Integer.toString(classId), student.getFaceImage(), Integer.toString(student.getId()), student.getName());
            } catch (IOException e) {
                System.out.println(MessageFormat.format("{0} 注册失败", student.getId()));
                throw new RuntimeException(e);
            }
            Thread.sleep(1000);
        }
        return new BaseResult(200, "测试没有问题");
    }

    /**
     * 教师：创建选人活动
     * @author 文旅航
     * @date 2024/7/7 21:04
     * @param addChooser
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.AddChooserVo>
    **/

    @PutMapping("/addChooser")
    public BaseResult<AddChooserVo> addChooser(@RequestBody AddChooserDto addChooser) throws Exception {
        log.info("创建选人活动");
        Activity activity = activityService.addChooser(addChooser);
        List<Student> students = studentService.getByClassId(addChooser.getClassId());
        if(addChooser.getType() == 0){
            List<Student> faceStudents = WebFaceDetect.getFaceImageVos(students, addChooser.getAnswerImage(), addChooser.getClassId());
            return new BaseResult<>(200, "获取成功", new AddChooserVo(faceStudents, activity.getId()));
        }
        else if(addChooser.getType() == 1) {
            return new BaseResult<>(200, "创建选人活动成功", new AddChooserVo(students, activity.getId()));
        }
        else{
            return new BaseResult<>(400, "无该类型选人活动");
        }
    }

    /**
     * 教师：查看活动详情
     * @author 文旅航
     * @date 0:32 2024/6/9
     * @param activityId
     * @return com.nsy.model.BaseResult
     **/

    @GetMapping("/{activityId}")
    public BaseResult<ActivityVo> signIn(@PathVariable Integer activityId) throws Exception {
        log.info("查看活动详情");
        return new BaseResult(200, "获取活动信息成功", activityService.getActivityVoById(activityId));
    }

    /**
     * 教师：获取所有活动
     * @author 文旅航
     * @date 2024/7/2 17:32
     * @param courseId
     * @param classId
     * @return com.nsy.BaseResult<java.util.List<com.nsy.vo.ActivityTypeVo>>
     **/
    @GetMapping("/allActivity")
    public BaseResult<List<ActivityMessageVo>> allActivity(@RequestParam Integer courseId, @RequestParam Integer classId){
        log.info("获得所有活动");
        List<ActivityMessageVo> activityMessageVos = activityService.getByAllMessage(courseId, classId);
        return new BaseResult<>(200, "获取所有活动成功", activityMessageVos);
    }

    /**
     * 教师：修改签到状态
     * @author 文旅航
     * @date 2024/7/2 19:48
     * @return com.nsy.BaseResult
     **/
    @PostMapping("sigin")
    public BaseResult updateSigin(@RequestBody UpdateSiginDto updateSiginDto){
        log.info("修改签到状态");
        studentActivityService.updateSigin(updateSiginDto);
        return new BaseResult(200, "修改签到状态成功");
    }

    /**
     * 教师：进行选人
     * @author 文旅航
     * @date 2024/7/2 22:03
     * @param activityId
     * @param studentId
     * @return com.nsy.BaseResult
     **/

    @PutMapping("chooser")
    public BaseResult chooseStudent(@RequestParam int activityId, @RequestParam Integer studentId) throws JsonProcessingException {
        log.info("进行选人");
        activityService.addChooser(activityId, studentId);
        return new BaseResult(200, "进行选人成功");
    }

    /**
     * 教师：删除活动
     * @author 文旅航
     * @date 2024/7/2 19:51
     * @return com.nsy.BaseResult
     **/

    @DeleteMapping("activity/{activityId}")
    public BaseResult deleteSigin(@PathVariable Integer activityId){
        log.info("教师删除活动");
        activityService.removeById(activityId);
        return new BaseResult(200, "删除活动成功");
    }

    /**
     * 学生：获取所有活动
     * @author 文旅航
     * @date 2024/7/6 22:50
     * @param courseId 课程id
     * @param studentId 学生id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.vo.ActivityMessageVo>>
    **/

    @GetMapping("studentAllActivity")
    public BaseResult<List<ActivityMessageVo>> studentAllActivity(@RequestParam Integer courseId, @RequestParam Integer studentId){
        log.info("获取所有活动");
        Student student = studentService.getById(studentId);
        return new BaseResult<>(200, "学生获取所有活动成功", activityService.getByAllMessage(courseId, student.getClassId()));
    }

    /**
     * 学生：获取签到活动详情
     * @author 文旅航
     * @date 2024/7/3 11:01
     * @param activityId
     * @param studentId
     * @return com.nsy.BaseResult<com.nsy.vo.StudentSiginVo>
     **/
    @GetMapping("studentSigin")
    public BaseResult<StudentSiginVo> getStudentSigin(@RequestParam Integer activityId, @RequestParam Integer studentId){
        log.info("学生获取签到活动情况");
        StudentSiginVo studentSiginVo = activityService.getSiginVoById(activityId, studentId);
        return new BaseResult<>(200, "获取签到活动情况成功",studentSiginVo);
    }

    /**
     * 学生：进行签到
     * @author 文旅航
     * @date 2024/7/3 10:29
     * @return com.nsy.BaseResult
     **/

    @PutMapping("studentSigin")
    public BaseResult studentSigin(@RequestBody StudentSiginDto studentSiginDto){
        Activity activity = activityService.getById(studentSiginDto.getActivityId());
        if(studentSiginDto.getType() == 2 || studentSiginDto.getType() == 3) {
            if(!activity.getAnswer().equals(studentSiginDto.getAnswer())){
                return new BaseResult(200, "签到码或手势错误");
            }
        }
        log.info(studentSiginDto.getStudentId() + " 学生签到成功");
        UpdateSiginDto updateSiginDto = new UpdateSiginDto(studentSiginDto.getActivityId(), studentSiginDto.getStudentId(), "已签");
        studentActivityService.updateSigin(updateSiginDto);
        return new BaseResult(200, "学生签到成功");
    }

    /**
     * 学生：获取选人活动详情
     * @author 文旅航
     * @date 2024/7/3 10:17
     * @param activityId
     * @return com.nsy.BaseResult<com.nsy.vo.ActivityVo>
     **/
    @GetMapping("studentChooser")
    public BaseResult<ActivityVo> getStudentChooser(@RequestParam Integer activityId) throws JsonProcessingException {
        log.info("学生选人活动详情");
        return new BaseResult<>(200, "学生获取选人活动情况", activityService.getActivityVoById(activityId));
    }

//    /**
//     * 教师：获取班级照片中每个人框出头像的图片
//     * @author 文旅航
//     * @date 2024/7/3 23:19
//     * @param classId
//     * @param image
//     * @return com.nsy.BaseResult<java.util.List<com.nsy.vo.FaceImageVo>>
//     **/
//    @PostMapping("getFaceImageVos")
//    public BaseResult<List<FaceImageVo>> getFaceImageVos(@RequestParam Integer classId, @RequestParam MultipartFile image) throws Exception {
//        log.info("获取班级照片中每个人框出头像的图片");
//        List<Student> students = studentService.getByClassId(classId);
//        return new BaseResult<>(200, "获取成功", WebFaceDetect.getFaceImageVos(students, image));
//    }

    /**
     * 工具：上传文件获得文件地址
     * @author 文旅航
     * @date 2024/7/4 9:49
     * @param image
     * @return com.nsy.BaseResult<java.lang.String>
     **/

    @PostMapping("getImagePath")
    public BaseResult<String> uploadImageFile(@RequestParam MultipartFile image){
        System.out.println("***********************文件进来了************************");
        log.info("上传图片");
        return new BaseResult<>(200, "上传图片成功", OSSUtils.uploadFileToOOS(image));
    }

    /**
     * 工具：上次多个文件获取文件地址集合
     * @author 文旅航
     * @date 2024/7/15 16:56
     * @param images
     * @return com.nsy.model.BaseResult<java.util.List<java.lang.String>>
    **/


    @PostMapping("getImagePaths")
    public BaseResult<List<String>> uploadImageFiles(@RequestParam List<MultipartFile> images){
        List<String> stringList = new ArrayList<>();
        for(MultipartFile image : images){
            stringList.add(OSSUtils.uploadFileToOOS(image));
        }
        log.info("上传多张图片");
        return new BaseResult<>(200, "上传图片成功", stringList);
    }

    /**
     * 获取鉴权信息
     * @author 文旅航
     * @date 2024/7/14 20:31
     * @return com.nsy.model.BaseResult<java.lang.String>
    **/

    @GetMapping("getSignature")
    public BaseResult<String> getSignature() throws Exception {
        return new BaseResult<>(200, "获取鉴权信息请求成功", BigModelNew.getRequestUrl());
    }

    /***
     *
     * @author 宁舒意
     * @date 12:31 2024/8/21
     * @return com.nsy.model.BaseResult<java.lang.String>
     */
    @GetMapping("getSignature2")
    public BaseResult<String> getSignature2() throws Exception {
        return new BaseResult<>(200, "获取鉴权信息请求成功", Main.getRequestUrl());
    }



}
