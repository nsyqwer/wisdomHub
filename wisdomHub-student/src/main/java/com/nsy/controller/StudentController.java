package com.nsy.controller;

import com.nsy.model.BaseResult;
import com.nsy.model.dto.StudentAssignmentDTO;
import com.nsy.model.pojo.Course;
import com.nsy.model.pojo.Resource;
import com.nsy.model.pojo.TaskPoint;
import com.nsy.model.vo.*;
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
     * @date 16:27 2024/5/16
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.ChapterMainVO>
    **/
    @GetMapping("/chapters/{courseId}")
    public BaseResult<ChapterMainVO> chaptersByCourseId(@PathVariable int courseId){
        ChapterMainVO chapterMainVo =new ChapterMainVO();
        return new BaseResult(200,"获取课程任务点成功",chapterMainVo);
    }


    /**
     * 学生：根据章节id获取任务点成功
     * @author 宁舒意
     * @date 19:39 2024/5/16
     * @param chapterId 章节ID
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.TaskPoint>>
    **/
    @GetMapping("/task_points/{chapterId}")
    public BaseResult<List<TaskPoint>> taskPointsByChapterId(@PathVariable int chapterId){
        List<TaskPoint> taskPointList =new ArrayList<>();
        return new BaseResult(200,"根据章节id获取任务点成功",taskPointList);
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

    /**
     * 学生：写作业,state这个字段，1表示未完成，2表示草稿，3表示未批改，4表示已完成
     * @author 宁舒意
     * @date 21:09 2024/5/16
     * @param studentAssignmentDTO
     * @return com.nsy.model.BaseResult
    **/
    @PutMapping("/assignment")
    public BaseResult assignment(@RequestBody StudentAssignmentDTO studentAssignmentDTO){
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
    public BaseResult<MistakeVo> mistakes(@RequestParam int studentId,@RequestParam int courseId){
        MistakeVo mistakeVo =new MistakeVo();
        return new BaseResult(200,"获取该课程错题集成功",mistakeVo);

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
















    









}
