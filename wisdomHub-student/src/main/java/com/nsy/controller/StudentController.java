package com.nsy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nsy.model.BaseResult;
import com.nsy.model.dto.AssignmentQuestionDTO;
import com.nsy.model.dto.StudentAssignmentDTO;
import com.nsy.model.pojo.*;
import com.nsy.model.vo.*;
import com.nsy.service.AssignmentService;
import com.nsy.service.ResourceService;
import com.nsy.service.StudentAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: StudentController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/15 16:22
 */

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentAssignmentService studentAssignmentService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    AssignmentService assignmentService;


    /**
     * 学生：查看我的课程
     * @author 宁舒意
     * @date 16:27 2024/5/16
     * @param studentId 学生id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Course>>
    **/
    @GetMapping("/courses/{studentId}")
    public BaseResult<List<Course>> coursesByStudentId(@PathVariable int studentId){
        List<Course> courseList = new ArrayList<>();
        return new BaseResult(200,"获取我的课程成功",courseList);
    }


   /**
    * 学生：查看某门课程任务点
    * @author 宁舒意
    * @date 8:17 2024/5/27
    * @param courseId 课程id
    * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Chapter>>
   **/
    @GetMapping("/chapters/{courseId}")
    public BaseResult<List<Chapter>> chaptersByCourseId(@PathVariable int courseId){
       List<Chapter> chapterList=new ArrayList<>();
        return new BaseResult(200,"获取课程章节任务点目录成功",chapterList);
    }





    /**
     * 学生：查看所有作业，或者已完成作业，或者未完成作业
     * @author 宁舒意
     * @date 20:41 2024/5/16
     * @param studentId 学生id
     * @param courseId 课程id
     * @param operation 操作id，0表示查询所有，1表示查询已完成的，2表示查询未完成的
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.vo.MyAssignmentVO>>
    **/
    @GetMapping("/assignments")
    public BaseResult<List<MyAssignmentVO>> assignments(@RequestParam int studentId,@RequestParam int courseId,@RequestParam int operation){
        //未批改状态也是已完成作业，还有一个状态是草稿
        List<MyAssignmentVO> myAssignmentVOList =new ArrayList<>();
        return new BaseResult(200,"获取成功",myAssignmentVOList);

    }

    //学生：查看作业详情 state（未完成0，待批阅1，已完成2）
    //首先是老师发布作业，content里面每个json对象都是AssignmentQuestionDTO对象
    //然后是学生写作业，写的时候AssignmentQuestionDTO里面的content没有答案，和解析
    //再是老师批改作业，批改完后，将答案和答案解析放进studentAssignDTO里面的content的题目中
    /**
     * 学生：查看作业详情
     * @author 宁舒意
     * @date 23:32 2024/6/3
     * @param studentId 学生id
     * @param assignmentId 作业id
     * @return com.nsy.model.BaseResult<com.nsy.model.pojo.StudentAssignment>
    **/
    @GetMapping("/assignment/{studentId}/{assignmentId}")
    public BaseResult<StudentAssignment> assignmentById(@PathVariable int studentId,@PathVariable int assignmentId){
        QueryWrapper<StudentAssignment> studentAssignmentQueryWrapper =new QueryWrapper<StudentAssignment>()
                .eq("student_id",studentId).eq("assignment_id",assignmentId);
        StudentAssignment studentAssignment = studentAssignmentService.getOne(studentAssignmentQueryWrapper);
        return new BaseResult(200,"获取作业详情成功",studentAssignment);
    }

    
    /**
     * 学生：写作业,其中state这个字段表示作业完成状态（未完成0，待批阅1，已完成2）
     * @author 宁舒意
     * @date 21:09 2024/5/16
     * @param studentAssignmentDTO
     * @return com.nsy.model.BaseResult
    **/
    @PutMapping("/assignment")
    public BaseResult assignment(@RequestBody StudentAssignmentDTO studentAssignmentDTO){
        StudentAssignment studentAssignment =new StudentAssignment();
        studentAssignment.setStudentId(studentAssignmentDTO.getStudentId());
        studentAssignment.setAssignmentId(studentAssignmentDTO.getAssignmentId());
        studentAssignment.setState(studentAssignmentDTO.getState());
        studentAssignment.setContent(studentAssignmentDTO.getContent());
        studentAssignmentService.save(studentAssignment);
        return new BaseResult(200,"成功");
    }

    //考试先不写
    /**
     * 学生：获取所有资料
     * @author 宁舒意
     * @date 21:28 2024/5/16
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult
    **/
    @GetMapping("/resources/{courseId}")
    public BaseResult<List<Resource>> resource(@PathVariable int courseId){
        QueryWrapper<Resource> resourceQueryWrapper =new QueryWrapper<Resource>().eq("course_id",courseId);
        List<Resource> resourceList =new ArrayList<>();
        return new BaseResult(200,"获取所有课程资源成功",resourceList);
    }

    //TODO 下载资料



    /**
     * 学生：学习记录
     * @author 宁舒意
     * @date 11:23 2024/5/17
     * @param studentId 学生id
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.StudyRecordVO>
    **/
    @GetMapping("/study-record")
    public BaseResult<StudyRecordVO> studyRecord(@RequestParam int studentId,@RequestParam int courseId){
        StudyRecordVO studyRecordVO =new StudyRecordVO();
        return new BaseResult(200,"获取学习记录成功",studyRecordVO);
    }


    /**
     * 学生：查看该课程错题集
     * @author 宁舒意
     * @date 11:33 2024/5/17
     * @param studentId 学生id
     * @param courseId  课程id
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.MistakeVo>
    **/
    @GetMapping("/mistakes")
    public BaseResult<List<MistakeVo>> mistakes(@RequestParam int studentId,@RequestParam int courseId){
        List<MistakeVo> mistakeVoList = new ArrayList<>();
        return new BaseResult(200,"获取该课程错题集成功",mistakeVoList);
    }


    /**
     * 学生：查看该学生所有课程错题集
     * @author 宁舒意
     * @date 0:08 2024/5/26
     * @param studentId 学生id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.vo.MistakeVo>>
    **/
    @GetMapping("/mistakes-all")
    public BaseResult<List<MistakeVo>> mistakeAll(@RequestParam int studentId){
        List<MistakeVo> mistakeVoList = new ArrayList<>();
        return new BaseResult(200,"获取所有课程错题集成功",mistakeVoList);
    }



    /**
     * 学生：查看错题详情
     * @author 宁舒意
     * @date 15:49 2024/5/17
     * @param mistakeId 错题id
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.MistakeDetailVO>
    **/
    @GetMapping("/mistake-detail/{mistakeId}")
    public BaseResult<MistakeDetailVO> mistakeDetail(@PathVariable int mistakeId){
        MistakeDetailVO mistakeDetailVO =new MistakeDetailVO();
        return new BaseResult(200,"获取错题详情成功",mistakeDetailVO);
    }



//    //学生进行签到
//    @PutMapping("")













    









}
