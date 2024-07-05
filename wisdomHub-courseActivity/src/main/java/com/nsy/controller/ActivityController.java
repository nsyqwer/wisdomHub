package com.nsy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsy.model.dto.BaseResult;
import com.nsy.model.dto.StudentSiginDto;
import com.nsy.model.dto.UpdateSiginDto;
import com.nsy.model.pojo.Activity;
import com.nsy.model.pojo.Student;
import com.nsy.service.ActivityService;
import com.nsy.service.StudentActivityService;
import com.nsy.service.StudentService;
import com.nsy.util.OSSUtils;
import com.nsy.util.xunfei.face.WebFaceDetect;
import com.nsy.model.vo.ActivityTypeVo;
import com.nsy.model.vo.ActivityVo;
import com.nsy.model.vo.FaceImageVo;
import com.nsy.model.vo.StudentSiginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 教师：创建签到或选人
     * @author 文旅航
     * @date 23:33 2024/6/4
     * @param activity 签到的实体
     * @return com.nsy.model.dto.BaseResult
     **/
    @PutMapping("/add")
    public BaseResult addActivity(@RequestBody Activity activity) throws Exception {
        activityService.add(activity);
        return new BaseResult(200,"创建活动成功");
    }

    /**
     * 教师：查看活动详情
     * @author 文旅航
     * @date 0:32 2024/6/9
     * @param activityId
     * @return com.nsy.model.dto.BaseResult
     **/

    @GetMapping("/{activityId}")
    public BaseResult<ActivityVo> signIn(@PathVariable Integer activityId) throws Exception {
        log.info("查看活动详情");
        return new BaseResult(200, "获取选人信息成功", activityService.getActivityVoById(activityId));
    }

    /**
     * 教师：获取所有活动
     * @author 文旅航
     * @date 2024/7/2 17:32
     * @param courseId
     * @param classId
     * @return com.nsy.model.dto.BaseResult<java.util.List<com.nsy.vo.ActivityTypeVo>>
    **/
    @GetMapping("allActivity")
    public BaseResult<List<ActivityTypeVo>> allActivity(@RequestParam Integer courseId, @RequestParam Integer classId){
        List<ActivityTypeVo> activityTypes = activityService.getByAllType(courseId, classId);
        return new BaseResult<>(200, "获取所有活动类型成功", activityTypes);
    }

    /**
     * 教师：修改签到状态
     * @author 文旅航
     * @date 2024/7/2 19:48
     * @return com.nsy.model.dto.BaseResult
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
     * @return com.nsy.model.dto.BaseResult
    **/

    @PutMapping("chooser")
    public BaseResult chooseStudent(@RequestParam int activityId, @RequestParam Integer studentId) throws JsonProcessingException {
        activityService.addChooser(activityId, studentId);
        log.info("进行选人");
        return new BaseResult(200, "进行选人成功");
    }

    /**
     * 教师：删除活动
     * @author 文旅航
     * @date 2024/7/2 19:51
     * @return com.nsy.model.dto.BaseResult
    **/

    @DeleteMapping("activity/{activityId}")
    public BaseResult deleteSigin(@PathVariable Integer activityId){
        activityService.removeById(activityId);
        log.info("教师删除活动");
        return new BaseResult(200, "删除活动成功");
    }

    /**
     * 学生：获取签到活动情况
     * @author 文旅航
     * @date 2024/7/3 11:01
     * @param activityId
     * @param studentId
     * @return com.nsy.model.dto.BaseResult<com.nsy.vo.StudentSiginVo>
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
     * @return com.nsy.model.dto.BaseResult
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
     * @return com.nsy.model.dto.BaseResult<com.nsy.vo.ActivityVo>
    **/
    @GetMapping("studentChooser")
    public BaseResult<ActivityVo> getStudentChooser(@RequestParam Integer activityId) throws JsonProcessingException {
        return new BaseResult<>(200, "学生获取选人活动情况", activityService.getActivityVoById(activityId));
    }

    /**
     * 测试类用不了，这个用来测试的接口
     * @author 文旅航
     * @date 2024/7/3 16:47
     * @return com.nsy.model.dto.BaseResult
    **/

    @GetMapping("test")
    public BaseResult get(@RequestParam MultipartFile image) throws Exception {
        OSSUtils.uploadFileToOOS(image);
        return new BaseResult(200, "测试成功");
    }

    /**
     * 教师：获取班级照片中每个人框出头像的图片
     * @author 文旅航
     * @date 2024/7/3 23:19
     * @param classId
     * @param image
     * @return com.nsy.model.dto.BaseResult<java.util.List<com.nsy.vo.FaceImageVo>>
    **/
    @GetMapping("getFaceImageVos")
    public BaseResult<List<FaceImageVo>> getFaceImageVos(@RequestParam Integer classId, @RequestParam MultipartFile image) throws Exception {
        log.info("获取班级照片中每个人框出头像的图片");
        List<Student> students = studentService.getByClassId(classId);
        return new BaseResult<>(200, "获取成功", WebFaceDetect.getFaceImageVos(students, image));
    }

    /**
     * 工具：上传图片获得图片地址
     * @author 文旅航
     * @date 2024/7/4 9:49
     * @param image
     * @return com.nsy.model.dto.BaseResult<java.lang.String>
    **/

    @GetMapping("getImagePath")
    public BaseResult<String> uploadImageFile(@RequestParam MultipartFile image){
        log.info("上传图片");
        return new BaseResult<>(200, "上传图片成功", OSSUtils.uploadFileToOOS(image));
    }
}
