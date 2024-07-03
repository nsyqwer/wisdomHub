package com.nsy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nsy.model.BaseResult;
import com.nsy.model.pojo.Chapter;
import com.nsy.model.pojo.Course;
import com.nsy.model.vo.StudyRecordVO;
import com.nsy.service.ChapterService;
import com.nsy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: CourseController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/6/10 21:14
 */
@RestController
@RequestMapping("/course")
public class CourseController {


    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    /**
     * 学生：查看我的课程
     * @author 宁舒意
     * @date 16:27 2024/5/16
     * @param studentId 学生id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Course>>
     **/
    @GetMapping("/courses/{studentId}")
    public BaseResult<List<Course>> coursesByStudentId(@PathVariable int studentId){
        List<Course> courseList = courseService.listByStudentId(studentId);
        return new BaseResult(200,"获取我的课程成功",courseList);
    }


    /**
     * 教师：查看我教的课
     * @author 宁舒意
     * @date 19:25 2024/5/19
     * @param teacherId 教师id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Course>>
     **/
    @GetMapping("/{teacherId}")
    public BaseResult<List<Course>> courses(@PathVariable Integer teacherId){
        List<Course> courseList = courseService.listByTeacherId(teacherId);
        return new BaseResult(200,"获取我教的课程成功",courseList);
    }


    //课程管理

    /**
     * 教师：创建课程
     * @author 宁舒意
     * @date 19:25 2024/5/19
     * @param course 课程实体
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("")
    public BaseResult saveCourse(@RequestBody Course course){
        courseService.save(course);
        return new BaseResult(200,"创建成功");
    }


    /**
     * 教师：删除课程
     * @author 宁舒意
     * @date 19:26 2024/5/19
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult
     **/
    @DeleteMapping("/{courseId}")
    public BaseResult removeCourse(@PathVariable int courseId){
        courseService.removeById(courseId);
        return new BaseResult<>(200,"删除成功");
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
        List<Chapter> chapterList=chapterService.list(new QueryWrapper<Chapter>()
                .eq("course_id",courseId));
        return new BaseResult(200,"获取课程章节任务点目录成功",chapterList);
    }


    /**
     * 教师：查看某节章节
     * @author 宁舒意
     * @date 21:15 2024/5/23
     * @param chapterId 章节id
     * @return com.nsy.model.BaseResult<com.nsy.model.dto.ChapterDetailDTO>
     **/
    @GetMapping("/chapter/{chapterId}")
    public BaseResult<Chapter> getChapters(@PathVariable Integer chapterId){
        Chapter chapter =chapterService.getById(chapterId);
        return new BaseResult(200,"获取章节成功", chapter);
    }

    /**
     * 教师：添加章节
     * @author 宁舒意
     * @date 8:22 2024/5/27
     * @param chapterList 章节集合
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("/chapter")
    public BaseResult addChapters(@RequestBody List<Chapter> chapterList){
        for (Chapter chapter : chapterList) {
            chapterService.save(chapter);
        }
        return new BaseResult<>(200,"添加章节成功");


    }


    /**
     * 教师：修改章节
     * @author 宁舒意
     * @date 8:23 2024/5/27
     * @param chapterList  章节集合
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("/chapter/update")
    public BaseResult putChapters(@RequestBody List<Chapter> chapterList){
        for (Chapter chapter : chapterList) {
            Chapter existingChapter = chapterService.getById(chapter.getId()); // 假设有获取章节的方法
            if (existingChapter == null) {
                chapterService.save(chapter); // 插入章节
            } else {
                chapterService.updateById(chapter); // 更新章节
            }
        }
        return new BaseResult<>(200,"修改章节成功");
    }


    /**
     * 教师：删除章节
     * @author 宁舒意
     * @date 19:27 2024/5/19
     * @param chapterId 章节id
     * @return com.nsy.model.BaseResult
     **/
    @DeleteMapping("/chapter/{chapterId}")
    public BaseResult deleteChapter(@PathVariable Integer chapterId){
        chapterService.removeById(chapterId);
        return new BaseResult<>(200,"章节删除成功");
    }


    /**
     * 学生：学习记录
     * @author 宁舒意
     * @date 11:23 2024/5/17
     * @param studentId 学生id
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.StudyRecordVO>
     **/
    @GetMapping("/study-record")
    public BaseResult<StudyRecordVO> studyRecord(@RequestParam int studentId, @RequestParam int courseId){
        StudyRecordVO studyRecordVO =new StudyRecordVO();

        return new BaseResult(200,"获取学习记录成功",studyRecordVO);
    }

}
