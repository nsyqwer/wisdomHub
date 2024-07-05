package com.nsy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.mapper.mapstruct.AssignmentDTOMapper;

import com.nsy.model.BaseResult;
import com.nsy.model.dto.*;
import com.nsy.model.dto.TeaCherAssignmentDTO;
import com.nsy.model.pojo.Assignment;
import com.nsy.model.pojo.Class;
import com.nsy.model.pojo.Course;
import com.nsy.model.pojo.StudentAssignment;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.model.vo.TeacherAssignVO;
import com.nsy.service.AssignmentService;
import com.nsy.service.ClassService;
import com.nsy.service.CourseService;
import com.nsy.service.StudentAssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @className: AssignmentController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/6/10 21:21
 ***
  *
 **/
@Slf4j
@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentAssignmentService studentAssignmentService;

    @Autowired
    private ClassService classService;

    /**
     * 学生：查看所有作业，或者已完成作业，或者未完成作业以及考试
     * @author 宁舒意
     * @date 20:41 2024/5/16
     * @param studentId 学生id
     * @param courseId 课程id
     * @param operation 操作id，0表示查询所有，1表示查询已完成的，2表示查询未完成的
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.vo.MyAssignmentVO>>
     **/

    @GetMapping("")
    public BaseResult<List<MyAssignmentVO>> assignments(@RequestParam int studentId, @RequestParam int courseId,  int operation,int type){
        //未批改状态也是已完成作业，还有一个状态是草稿(草稿设置成未提交就行了)
        List<MyAssignmentVO> myAssignmentVOList = assignmentService.listToVO(studentId,courseId,operation,type);
        return new BaseResult(200,"获取成功",myAssignmentVOList);

    }

    //学生：查看作业详情 state（未完成0，待批阅1，已完成2）
    //首先是老师发布作业，content里面每个json对象都是AssignmentQuestionDTO对象
    //然后是学生写作业，写的时候AssignmentQuestionDTO里面的content没有答案，和解析
    //再是老师批改作业，批改完后，将答案和答案解析放进studentAssignDTO里面的content的题目中
    /**
     * 学生：查看作业或考试详情
     * @author 宁舒意
     * @date 23:32 2024/6/3
     * @param studentId 学生id
     * @param assignmentId 作业id
     * @return com.nsy.model.BaseResult<com.nsy.model.pojo.StudentAssignment>
     **/
    @GetMapping("/{studentId}/{assignmentId}")
    public BaseResult<StudentAssignment> assignmentById(@PathVariable int studentId,@PathVariable int assignmentId){
        //TODO 这里作业的状态不同，应该对应不同的界面
        StudentAssignment studentAssignment = studentAssignmentService.getOne(
                new QueryWrapper<StudentAssignment>()
                        .eq("student_id", studentId)
                        .eq("assignment_id", assignmentId)
        );
        return new BaseResult(200,"获取作业详情成功",studentAssignment);
    }


    /**
     * 学生：写作业,其中state这个字段表示作业完成状态（未完成0，待批阅1，已完成2）
     * 进行考试
     * @author 宁舒意
     * @date 21:09 2024/5/16
     * @param studentAssignmentDTO
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("")
    public BaseResult assignment(@RequestBody StudentAssignmentDTO studentAssignmentDTO) throws JsonProcessingException {
        studentAssignmentService.writeAssignment(studentAssignmentDTO);
        return new BaseResult(200,"成功");
    }





    //作业管理

    //老师查看所有作业，添加作业，删除作业，编辑作业，批改学生作业


    /**
     * 教师查看作业或者考试
     * @author 宁舒意
     * @date 17:13 2024/7/5
     * @param teaCherAssignmentDTO
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.vo.TeacherAssignVO>>
     */
    @GetMapping("/teacher/")
    public BaseResult<List<TeacherAssignVO>> getAssignByCId(@RequestParam TeaCherAssignmentDTO teaCherAssignmentDTO){
        List<TeacherAssignVO> teacherAssignVOList =assignmentService.getTeacherAssignVO(teaCherAssignmentDTO);
        return new BaseResult<>(200,"教师查看作业或者考试概况",teacherAssignVOList);
    }

    /**
     * 教师：添加作业,添加考试
     * assignmentId(传了id就是编辑，没传就是新增)
     * @author 宁舒意
     * @date 21:45 2024/6/3
     * @param assignmentAddDTO
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("/teacher")
    public BaseResult assignment(@RequestBody AssignmentAddDTO assignmentAddDTO) throws JsonProcessingException {
        //state设置为0表示草稿
        Assignment assignment =new Assignment();
        if (assignmentAddDTO.getAssignmentId() != null) {
            //表示编辑
         assignment =assignmentService.getById(assignmentAddDTO.getAssignmentId());
        }
        AssignmentDTOMapper.INSTANCE.AddDTOtoAssignment(assignmentAddDTO,assignment);

        assignment.setState(0);
        Course course=courseService.getById(assignmentAddDTO.getCourseId());
        assignment.setCourseName(course.getCourseName());
        BigDecimal totalScore = BigDecimal.ZERO; // 初始化总和为0

        for (AssignmentQuestionDTO dto : assignmentAddDTO.getContent()) {
            totalScore = totalScore.add(dto.getQuestionScore()); // 将每个对象的 questionScore 字段值加到总和中
        }
        assignment.setScore(totalScore);
        assignmentService.save(assignment);
        System.out.println("这"+assignment);
        return new BaseResult(200,"添加或编辑作业考试成功",assignment.getId());
    }


    /**
     * 老师：获取所有班级
     * @author 宁舒意
     * @date 11:40 2024/7/5
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Class>>
     */
    @GetMapping("/class/")
    public BaseResult<List<Class>> getAllClass(){
        return new BaseResult<>(200,"所有班级", classService.list());
    }

    /**
     * 教师：发布作业或者考试
     * @author 宁舒意
     * @date 23:27 2024/6/3
     * @param assignmentPublishDTO
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("/publish")
    public BaseResult publishAssignment(@RequestBody AssignmentPublishDTO assignmentPublishDTO) throws IOException {
       assignmentService.publishAssignment(assignmentPublishDTO);
        return new BaseResult(200,"发布成功");
    }



    /**
     * 教师: 删除作业或者考试
     * @author 宁舒意
     * @date 19:28 2024/5/19
     * @param assignmentId 作业id
     * @return com.nsy.model.BaseResult
     **/
    @DeleteMapping("/{assignmentId}")
    public BaseResult assignment(@PathVariable Integer assignmentId){
        assignmentService.removeById(assignmentId);
        return new BaseResult(200,"删除成功");
    }

    /**
     * 教师：修改作业或者考试状态
     * @author 宁舒意
     * @date 19:28 2024/5/19
     * @param assignmentId 作业id
     * @param state 状态
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("/state")
    public BaseResult assignmentState(@RequestParam Integer assignmentId, @RequestParam Integer state){
        Assignment assignment =new Assignment();
        assignment.setId(assignmentId);
        assignment.setState(state);
        assignmentService.updateById(assignment);
        return new BaseResult(200,"修改作业状态成功");
    }


    /**
     * 教师：批改作业或者考试
     * @author 宁舒意
     * @date 21:13 2024/6/4
     * @param assignmentCorrectDTO
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("/correct")
    public BaseResult correctAssignment(@RequestBody AssignmentCorrectDTO assignmentCorrectDTO) throws IOException {
        QueryWrapper<StudentAssignment> studentAssignmentQueryWrapper =new QueryWrapper<StudentAssignment>()
                .eq("assignment_id",assignmentCorrectDTO.getAssignmentId())
                .eq("student_id",assignmentCorrectDTO.getStudentId());
        StudentAssignment studentAssignment =studentAssignmentService.getOne(studentAssignmentQueryWrapper);
        studentAssignment.setTeacherId(assignmentCorrectDTO.getTeacherId());
        studentAssignment.setComment(assignmentCorrectDTO.getComment());

        ObjectMapper objectMapper = new ObjectMapper();
        String questionJson =objectMapper.writeValueAsString(assignmentCorrectDTO.getContent());
        studentAssignment.setContent(questionJson);
        //遍历每道题目算出总分
        BigDecimal studentAllScore =BigDecimal.ZERO;
        for (AssignmentQuestionDTO assignmentQuestionDTO : assignmentCorrectDTO.getContent()) {
            studentAllScore.add(assignmentQuestionDTO.getStudentScore());
        }
        studentAssignment.setStudentScore(studentAllScore);
        studentAssignmentService.updateById(studentAssignment);
        return new BaseResult(200,"批改成功");
    }

}
