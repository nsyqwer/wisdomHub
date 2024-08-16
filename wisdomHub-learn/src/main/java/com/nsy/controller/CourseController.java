package com.nsy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.model.BaseResult;
import com.nsy.model.dto.CourseSetClassDTO;
import com.nsy.model.dto.KnowledgeDTO;
import com.nsy.model.pojo.Chapter;
import com.nsy.model.pojo.Course;
import com.nsy.model.pojo.Question;
import com.nsy.model.pojo.TeacherCourse;
import com.nsy.model.vo.StudyRecordVO;
import com.nsy.service.*;
import com.nsy.util.xunfei.text_moderation.TextMain;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @className: CourseController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/6/10 21:14
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {


    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @DubboReference
    private AIDubboService aiDubboService;

    /**
     * 设置哪些班学习那个课程
    **/
    @PutMapping("/set_class")
    public BaseResult setClass(@RequestBody CourseSetClassDTO courseSetClassDTO){
       studentCourseService.setClass(courseSetClassDTO);
        return new BaseResult(200,"添加成功");
    }

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

    /**
     * 教师：获取课程详情
     * @author 宁舒意
     * @date 21:40 2024/7/8
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult<com.nsy.model.pojo.Course>
     */
    @GetMapping("detail/{courseId}")
    public BaseResult<Course> course(@PathVariable Integer courseId){
        Course course =courseService.getById(courseId);
        return new BaseResult<>(200,"获取课程详情",course);
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
    public BaseResult saveCourse(@RequestBody Course course,@RequestParam Integer teacherId){
        courseService.save(course);
        TeacherCourse teacherCourse=new TeacherCourse();
        teacherCourse.setCourseId(course.getId());
        teacherCourse.setCourseName(course.getCourseName());
        teacherCourse.setCourseImage(course.getImage());
        teacherCourse.setTeacherId(teacherId);
        teacherCourseService.save(teacherCourse);
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
     * @param chapter 章节集合
     * @return com.nsy.BaseResult<java.util.List<java.lang.String>> 若有文本违规，则返回违规问题
     **/
    @PutMapping("/chapter")
    public BaseResult<List<String>> addChapters(@RequestBody Chapter chapter) throws Exception {
        //List<String> violations = TextMain.getViolations(chapter.getContent());

       // if(violations != null && chapter.getType().equals("text")){
          //  System.out.println("*******************文本不合规***************");
           // return new BaseResult<>(400, "上传文本违规", violations);
        //}

        chapterService.save(chapter);
        return new BaseResult<>(200,"添加章节成功");
    }

    /**
     * 教师：修改章节
     * @author 宁舒意
     * @date 8:23 2024/5/27
     * @param chapter  章节集合
     * @return com.nsy.BaseResult<java.util.List<java.lang.String>> 若有文本违规，则返回违规问题
     **/
    @PutMapping("/chapter/update")
    public BaseResult<List<String>> putChapters(@RequestBody Chapter chapter) throws Exception {
        List<String> violations = TextMain.getViolations(chapter.getContent());

        if(violations != null && chapter.getType().equals("text")){
            System.out.println("*******************文本不合规***************");
            return new BaseResult<>(400, "上传文本违规", violations);
        }
            chapterService.updateById(chapter); // 更新章节
        return new BaseResult<>(200, "修改章节成功");
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
     * 根据课程id生成知识图谱
     * @author 宁舒意
     * @date 11:40 2024/7/14
     * @param courseId
     * @return com.nsy.model.BaseResult
     */
    @PutMapping("/create-knowledge-graph")
    public BaseResult createKnowledgeGraph(Integer courseId) throws IOException {

        List<Chapter> chapterList = chapterService.list(new QueryWrapper<Chapter>().eq("course_id",courseId).eq("type","text"));
        // 遍历章节列表
        StringBuilder combinedContent = new StringBuilder();
        for (Chapter chapter : chapterList) {
            // 获取每个章节的内容并追加到StringBuilder
            combinedContent.append(chapter.getContent());
        }

        // 获取最终的拼接结果
        String finalContent = combinedContent.toString();

        aiDubboService.createKnowledgeGraph(finalContent,courseId);




        return new BaseResult(200,"成功");
    }

    /**
     * 根据课程id获取知识图谱
     * @author 宁舒意
     * @date 10:37 2024/7/14
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult
     */
    @GetMapping("/get-knowledge-graph/{courseId}")
    public BaseResult getKnowledgeGraph(@PathVariable Integer courseId){
        KnowledgeDTO knowledgeDTO =aiDubboService.getKnowledgeByCID(courseId);
        return new BaseResult(200,"成功",knowledgeDTO);
    }




    /**
     * 根据课程id创建思维导图
     * @author 宁舒意
     * @date 20:41 2024/7/14
     * @param courseId
     * @return com.nsy.model.BaseResult
     */
    @GetMapping(value = "/create-mindMap/{courseId}")
    public BaseResult createMindMap(@PathVariable Integer courseId) throws JsonProcessingException {

        List<Chapter> chapterList = chapterService.list(new QueryWrapper<Chapter>().eq("course_id",courseId).eq("type","text"));
        // 遍历章节列表
        StringBuilder combinedContent = new StringBuilder();
        for (Chapter chapter : chapterList) {
            // 获取每个章节的内容并追加到StringBuilder
            combinedContent.append(chapter.getContent());
        }

        // 获取最终的拼接结果
        String finalContent = combinedContent.toString();
        String answer=aiDubboService.createMindMap(finalContent);
        Course course =courseService.getById(courseId);
        course.setMindMap(answer);
        //思维导图是空的就直接保存，否则就更新
        if(Objects.isNull(course.getMindMap())){
            courseService.save(course);
        }else {
            courseService.updateById(course);
        }
        return new BaseResult(200,"生成的思维导图",answer);
    }


    /**
     * 保存思维导图
     * @author 宁舒意
     * @date 11:34 2024/8/16
     * @param courseId
     * @param markdown
     * @return com.nsy.model.BaseResult
     */
    @PutMapping("/save")
    public BaseResult saveMinMap(@RequestParam Integer courseId,@RequestParam String markdown){
        Course course =courseService.getById(courseId);
        course.setMindMap(markdown);
        courseService.updateById(course);
        return new BaseResult(200,"保存思维导图成功");
    }

    /**
     * 根据课程id获取思维导图
     * @author 宁舒意
     * @date 16:02 2024/8/14
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult
     */
    @GetMapping("/get-mindMap/{courseId}")
    public BaseResult getMindMap(@PathVariable Integer courseId){
        Course course =courseService.getById(courseId);
        String markdown = course.getMindMap();
        if(markdown==null){
            return new BaseResult(410,"思维导图还未生成",markdown);
        }else {
            return new BaseResult(200,"获取该课程思维导图成功",markdown);
        }
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
        StudyRecordVO studyRecordVO =courseService.getStudentRecordVO( studentId,courseId);
        return new BaseResult(200,"获取学习记录成功",studyRecordVO);
    }



    /**
     * 根据输入，创建ppt
     * @author 宁舒意
     * @date 1:57 2024/7/12
     * @param query 根据输入生成ppt
     * @return com.nsy.model.BaseResult
     */
    @GetMapping("/createPPT")
    public BaseResult createPPT(String query) throws IOException, InterruptedException {
        String URL = aiDubboService.createPPT(query);
        return new BaseResult(200,"根据输入生成ppt成功",URL);
    }



    /**
     * ai智能推荐学习路径
     * @author 宁舒意
     * @date 2:08 2024/7/16
     * @param query 随便传什么
     * @return com.nsy.model.BaseResult
     */
    @GetMapping("/createPath")
    public BaseResult createPath(String query){
        String pathString = aiDubboService.createPath("学java中");
        return new BaseResult(200,"智能路径推荐",pathString);
    }



}
