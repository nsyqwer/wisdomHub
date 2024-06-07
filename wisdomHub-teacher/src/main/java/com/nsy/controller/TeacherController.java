package com.nsy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.mapper.mapstruct.AssignmentDTOMapper;
import com.nsy.model.BaseResult;

import com.nsy.model.dto.AssignmentAddDTO;
import com.nsy.model.dto.AssignmentCorrectDTO;
import com.nsy.model.dto.AssignmentPulishDTO;
import com.nsy.model.dto.AssignmentQuestionDTO;
import com.nsy.model.pojo.*;

import com.nsy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @className: TeacherController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/17 16:23
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CourseService courseService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private StudentAssignmentService studentAssignmentService;

    @Autowired
    private SigninService signinService;



    //题库管理

    /**
     * 教师：查看该课程所有题目
     * @author 宁舒意
     * @date 8:32 2024/5/27
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Question>>
    **/
    @GetMapping("/questions-all/{courseId}")
    public BaseResult<List<Question>> allQuestion(@PathVariable int courseId){
        List<Question> questionList =new ArrayList<>();
        return new BaseResult<>(200,"获取课程所有题目",questionList);
    }

    //添加题目，删除题目，查看题目详情，导入题目（AI）
    /**
     * 教师：添加题目
     * @author 宁舒意
     * @date 19:24 2024/5/19
     * @param questions List<Question> questions，泛型为Question的集合
     * @return com.nsy.model.BaseResult
    **/
    @PutMapping("/questions")
    public BaseResult questions(@RequestBody List<Question> questions){
        //需要前端传过来的question的connect字段本身就是Json字符串
        for (Question question : questions) {
            questionService.save(question);
        }
        return new BaseResult(200,"添加成功");
    }

    /**
     * 教师：修改题目
     * @author 宁舒意
     * @date 19:24 2024/5/19
     * @param question  题目实体
     * @return com.nsy.model.BaseResult
    **/
    @PutMapping("/question")
    public BaseResult questions(@RequestBody Question question){
        questionService.updateById(question);
        return new BaseResult(200,"修改成功");
    }


    /**
     * 教师：删除题目
     * @author 宁舒意
     * @date 19:25 2024/5/19
     * @param questionId 题目id
     * @return com.nsy.model.BaseResult
    **/
    @DeleteMapping("/question/{questionId}")
    public BaseResult question(@PathVariable int questionId){
        questionService.removeById(questionId);
        return new BaseResult(200,"删除成功");
    }

    //TODO 导入题目（AI）

    /**
     * 教师：题目详情
     * @author 宁舒意
     * @date 19:25 2024/5/19
     * @param questionId 题目id
     * @return com.nsy.model.BaseResult
    **/
    @GetMapping("/question/{questionId}")
    public BaseResult getQuestion(@PathVariable Integer questionId){
        Question question =questionService.getById(questionId);
        return new BaseResult(200,"获取成功",question);
    }


    /**
     * 教师：查看我教的课
     * @author 宁舒意
     * @date 19:25 2024/5/19
     * @param teacherId 教师id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Course>>
    **/
    @GetMapping("/courses/{teacherId}")
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
    @PutMapping("/course")
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
    @DeleteMapping("/course/{courseId}")
    public BaseResult removeCourse(@PathVariable int courseId){
        courseService.removeById(courseId);
        return new BaseResult<>(200,"删除成功");
    }

    /**
     * 教师：查看某节章节
     * @author 宁舒意
     * @date 21:15 2024/5/23
     * @param chapterId 章节id
     * @return com.nsy.model.BaseResult<com.nsy.model.dto.ChapterDetailDTO>
    **/
    @GetMapping("/chapters/{chapterId}")
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
    @PutMapping("/chapters-add")
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
    @PutMapping("/chapters-update")
    public BaseResult putChapters(@RequestBody List<Chapter> chapterList){
        for (Chapter chapter : chapterList) {
            chapterService.updateById(chapter);
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


    //作业管理

    //添加作业，删除作业，编辑作业，批改学生作业
    /**
     * 教师：添加作业
     * @author 宁舒意
     * @date 21:45 2024/6/3
     * @param assignmentAddDTO
     * @return com.nsy.model.BaseResult
    **/
    @PutMapping("/assignment")
    public BaseResult assignment(@RequestBody AssignmentAddDTO assignmentAddDTO) throws JsonProcessingException {
        //state设置为0表示草稿
        Assignment assignment =new Assignment();
        AssignmentDTOMapper.INSTANCE.AddDTOtoAssignment(assignmentAddDTO,assignment);
        assignment.setState(0);
        Course course=courseService.getById(assignmentAddDTO.getCourseId());
        assignment.setCourseName(course.getCourseName());
        //json数组转对象集合
        ObjectMapper objectMapper = new ObjectMapper();
        List<AssignmentQuestionDTO> assignmentQuestionDTOList = Arrays
                .asList(objectMapper.readValue(assignmentAddDTO.getContent(), AssignmentQuestionDTO[].class));
        BigDecimal totalScore = BigDecimal.ZERO; // 初始化总和为0

        for (AssignmentQuestionDTO dto : assignmentQuestionDTOList) {
            totalScore = totalScore.add(dto.getQuestionScore()); // 将每个对象的 questionScore 字段值加到总和中
        }
        assignment.setScore(totalScore);
        assignmentService.save(assignment);
        return new BaseResult(200,"添加或编辑作业成功");
    }

    /**
     * 教师：发布作业
     * @author 宁舒意
     * @date 23:27 2024/6/3
     * @param assignmentPulishDTO
     * @return com.nsy.model.BaseResult
    **/
    @PutMapping("/assignment-publish")
    public BaseResult publishAssignment(@RequestBody AssignmentPulishDTO assignmentPulishDTO){
        Assignment assignment =assignmentService.getById(assignmentPulishDTO.getAssignmentId());
        AssignmentDTOMapper.INSTANCE.PublishDTOtoAssignment(assignmentPulishDTO,assignment);
        //将作业状态改为1（进行中）
        assignment.setState(1);
        assignmentService.updateById(assignment);
        //将每个学生都加入到作业中来
        List<Integer> studentIds =studentAssignmentService.findStudentIdsByClassIds(assignmentPulishDTO.getClassIdList());
        StudentAssignment studentAssignment =new StudentAssignment();
        studentAssignment.setAssignmentId(assignment.getId());
        studentAssignment.setState(0);//0表示未完成
        studentAssignment.setStudentScore(BigDecimal.ZERO);
        //TODO 这里的content是assignmentQuestion的数组，发布的时候答案和答案解析不能传过去
        studentAssignment.setContent(assignment.getContent());
        for (Integer studentId : studentIds) {
            studentAssignment.setStudentId(studentId);
            studentAssignmentService.save(studentAssignment);
        }
        return new BaseResult(200,"发布成功");


    }



    /**
     * 教师: 删除作业
     * @author 宁舒意
     * @date 19:28 2024/5/19
     * @param assignmentId 作业id
     * @return com.nsy.model.BaseResult
    **/
    @DeleteMapping("/assignment/{assignmentId}")
    public BaseResult assignment(@PathVariable Integer assignmentId){
        assignmentService.removeById(assignmentId);
        return new BaseResult(200,"删除成功");
    }

    /**
     * 教师：修改作业状态
     * @author 宁舒意
     * @date 19:28 2024/5/19
     * @param assignmentId 作业id
     * @param state 状态
     * @return com.nsy.model.BaseResult
    **/
    @PutMapping("/assignment/state")
    public BaseResult assignmentState(@RequestParam Integer assignmentId,@RequestParam Integer state){
        Assignment assignment =new Assignment();
        assignment.setId(assignmentId);
        assignment.setState(state);
        assignmentService.updateById(assignment);
        return new BaseResult(200,"修改作业状态成功");
    }


    /**
     * 教师：批改作业
     * @author 宁舒意
     * @date 21:13 2024/6/4
     * @param assignmentCorrectDTO
     * @return com.nsy.model.BaseResult
    **/
    @PutMapping("/assignment/correct")
    public BaseResult correctAssignment(@RequestBody AssignmentCorrectDTO assignmentCorrectDTO) throws IOException {
        QueryWrapper<StudentAssignment> studentAssignmentQueryWrapper =new QueryWrapper<StudentAssignment>()
                .eq("assignment_id",assignmentCorrectDTO.getAssignmentId()).eq("student_id",assignmentCorrectDTO.getStudentId());
        StudentAssignment studentAssignment =studentAssignmentService.getOne(studentAssignmentQueryWrapper);
        studentAssignment.setTeacherId(assignmentCorrectDTO.getTeacherId());
        studentAssignment.setComment(assignmentCorrectDTO.getComment());
        studentAssignment.setContent(assignmentCorrectDTO.getContent());
        //json数组转对象集合，然后遍历每道题目算出总分
        List<AssignmentQuestionDTO> assignmentQuestionDTOList = assignmentService.getQuestion(assignmentCorrectDTO.getContent());
        BigDecimal studentAllScore =BigDecimal.ZERO;
        for (AssignmentQuestionDTO assignmentQuestionDTO : assignmentQuestionDTOList) {
            studentAllScore.add(assignmentQuestionDTO.getStudentScore());
        }
        studentAssignment.setStudentScore(studentAllScore);
        studentAssignmentService.updateById(studentAssignment);
        return new BaseResult(200,"批改成功");
    }



    /**
     * 创建签到
     * @author 宁舒意
     * @date 23:33 2024/6/4
     * @param signin 签到的实体
     * @return com.nsy.model.BaseResult
    **/
    @PutMapping("/signin")
    public BaseResult signIn(@RequestBody Signin signin){
        signinService.save(signin);
        return new BaseResult(200,"创建签到成功");
    }


















}
