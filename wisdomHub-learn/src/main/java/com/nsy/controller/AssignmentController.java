package com.nsy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsy.dto.*;
import com.nsy.mapper.mapstruct.AssignmentDTOMapper;
import com.nsy.BaseResult;
import com.nsy.dto.*;
import com.nsy.pojo.Assignment;
import com.nsy.pojo.Course;
import com.nsy.pojo.StudentAssignment;
import com.nsy.vo.MyAssignmentVO;
import com.nsy.service.AssignmentService;
import com.nsy.service.CourseService;
import com.nsy.service.StudentAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: AssignmentController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/6/10 21:21
 */
@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentAssignmentService studentAssignmentService;


    /**
     * 学生：查看所有作业，或者已完成作业，或者未完成作业
     * @author 宁舒意
     * @date 20:41 2024/5/16
     * @param studentId 学生id
     * @param courseId 课程id
     * @param operation 操作id，0表示查询所有，1表示查询已完成的，2表示查询未完成的
     * @return com.nsy.BaseResult<java.util.List<com.nsy.model.vo.MyAssignmentVO>>
     **/
    @GetMapping("/assignments")
    public BaseResult<List<MyAssignmentVO>> assignments(@RequestParam int studentId, @RequestParam int courseId, @RequestParam int operation){
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
     * @return com.nsy.BaseResult<com.nsy.model.pojo.StudentAssignment>
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
     * @return com.nsy.BaseResult
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




    //作业管理

    //添加作业，删除作业，编辑作业，批改学生作业
    /**
     * 教师：添加作业
     * @author 宁舒意
     * @date 21:45 2024/6/3
     * @param assignmentAddDTO
     * @return com.nsy.BaseResult
     **/
    @PutMapping("")
    public BaseResult assignment(@RequestBody AssignmentAddDTO assignmentAddDTO) throws JsonProcessingException {
        //state设置为0表示草稿
        Assignment assignment =new Assignment();
        AssignmentDTOMapper.INSTANCE.AddDTOtoAssignment(assignmentAddDTO,assignment);
        assignment.setState(0);
        Course course=courseService.getById(assignmentAddDTO.getCourseId());
        assignment.setCourseName(course.getCourseName());
//        //json数组转对象集合
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<AssignmentQuestionDTO> assignmentQuestionDTOList = Arrays
//                .asList(objectMapper.readValue(assignmentAddDTO.getContent(), AssignmentQuestionDTO[].class));
        BigDecimal totalScore = BigDecimal.ZERO; // 初始化总和为0

        for (AssignmentQuestionDTO dto : assignmentAddDTO.getContent()) {
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
     * @param assignmentPublishDTO
     * @return com.nsy.BaseResult
     **/
    @PutMapping("/publish")
    public BaseResult publishAssignment(@RequestBody AssignmentPublishDTO assignmentPublishDTO){
        Assignment assignment =assignmentService.getById(assignmentPublishDTO.getAssignmentId());
        AssignmentDTOMapper.INSTANCE.PublishDTOtoAssignment(assignmentPublishDTO,assignment);
        //将作业状态改为1（进行中）
        assignment.setState(1);
        assignmentService.updateById(assignment);
        //将每个学生都加入到作业中来
        List<Integer> studentIds =studentAssignmentService.findStudentIdsByClassIds(assignmentPublishDTO.getClassIdList());
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
     * @return com.nsy.BaseResult
     **/
    @DeleteMapping("/{assignmentId}")
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
     * @return com.nsy.BaseResult
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
     * 教师：批改作业
     * @author 宁舒意
     * @date 21:13 2024/6/4
     * @param assignmentCorrectDTO
     * @return com.nsy.BaseResult
     **/
    @PutMapping("/correct")
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

}
