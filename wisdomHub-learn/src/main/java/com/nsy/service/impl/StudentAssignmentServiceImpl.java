package com.nsy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.constant.AssignmentTypeEnum;
import com.nsy.mapper.*;
import com.nsy.mapper.mapstruct.TestPaperDtoMapper;
import com.nsy.model.dto.*;
import com.nsy.model.pojo.*;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.model.vo.TestStudentAnswerVo;
import com.nsy.service.StudentAssignmentService;
import com.nsy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author 宁舒意
* @description 针对表【student_assignment】的数据库操作Service实现
* @createDate 2024-05-27 08:37:50
*/
@Service
public class StudentAssignmentServiceImpl extends ServiceImpl<StudentAssignmentMapper, StudentAssignment>
    implements StudentAssignmentService {

    @Autowired
    private StudentAssignmentMapper studentAssignmentMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private TestPaperDtoMapper testPaperDtoMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TestPaperMapper testPaperMapper;

    public List<Integer> findStudentIdsByClassIds(List<Integer> classIds) {
        // 将 List<Integer> 转换为逗号分隔的字符串
        String classIdsStr = String.join(",", classIds.stream().map(String::valueOf).toArray(String[]::new));
        return studentAssignmentMapper.findStudentIdsByClassIds(classIdsStr);
    }



    @Override
    public void writeAssignment(StudentAssignmentDTO studentAssignmentDTO) throws JsonProcessingException {

        StudentAssignment studentAssignment =studentAssignmentMapper.selectOne(
                new QueryWrapper<StudentAssignment>()
                        .eq("student_id", studentAssignmentDTO.getStudentId())
                        .eq("assignment_id", studentAssignmentDTO.getAssignmentId())
        );

        if(Objects.equals(studentAssignmentDTO.getOperation(),1)){
            //operation为1表示保存
            studentAssignment.setState(0);
        }else if (Objects.equals(studentAssignmentDTO.getOperation(),2)){
            //operation为2表示提交，将学生作业状态改成1，表示待批改
            studentAssignment.setState(1);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String questionJson =objectMapper.writeValueAsString(studentAssignmentDTO.getContent());
        studentAssignment.setContent(questionJson);

        int type =studentAssignmentDTO.getType();
        if(Objects.equals(type, AssignmentTypeEnum.EXAM.getCode())){
            studentAssignment.setExamBeginTime(studentAssignmentDTO.getExamBeginTime());
            studentAssignment.setExamEndTime(studentAssignmentDTO.getExamEndTime());
        }
        studentAssignmentMapper.updateById(studentAssignment);


    }

    @Override
    public void saveAssignments(Integer id, SaveTestPaperDto saveTestPaperDto, Integer state) {
        List<StudentTestPaperInfo> studentTestPapers = saveTestPaperDto.getStudentTestPapers();
        for(StudentTestPaperInfo studentInfo: studentTestPapers){
            StudentAssignment studentAssignment = testPaperDtoMapper.saveStudentInfoToPojo(studentInfo);
            studentAssignment.setAssignmentId(id);
            studentAssignment.setType(3);
            studentAssignment.setTeacherId(saveTestPaperDto.getTeacherId());
            studentAssignment.setCourseId(saveTestPaperDto.getCourseId());
            studentAssignment.setTitle(saveTestPaperDto.getTitle());

            studentAssignment.setState(state);

            Integer studentId = studentMapper.selectIdBySnoAndName(studentInfo.getNumber(), studentInfo.getName());

            studentAssignment.setStudentId(studentId);

            if(studentAssignment.getAssignmentId() != null){
                StudentAssignment studentAssignment1 = studentAssignmentMapper.selectById(studentAssignment.getAssignmentId());
                if(studentAssignment1 == null){
                    studentAssignmentMapper.insert(studentAssignment);
                }
                else{
                    studentAssignmentMapper.updateById(studentAssignment);
                }
            }
        }
    }

    @Override
    public void saveTestPaperStudent(TestPaperStudentAnswer studentAnswer) {
        String testPaperImages = JSON.toJSONString(studentAnswer.getTestPaperImages());

        StudentAssignment studentAssignment = testPaperDtoMapper.studentAnswerToStudentAssignment(studentAnswer);
        studentAssignment.setTestPaperImages(testPaperImages);

        //插入学生id
        Integer studentId = studentMapper.selectIdBySnoAndName(studentAnswer.getNumber(), studentAnswer.getName());
        studentAssignment.setStudentId(studentId);

        TestPaper testPaper = testPaperMapper.selectById(studentAnswer.getTestId());

        studentAssignment.setCourseId(testPaper.getCourseId());
        studentAssignment.setTitle(testPaper.getTitle());
        studentAssignment.setTeacherId(testPaper.getTeacherId());
        studentAssignment.setType(2);
        studentAssignment.setState(2);

        StudentAssignment studentAssignment1 = studentAssignmentMapper.selectByImages(studentAnswer.getTestId(), testPaperImages);

        System.out.println("学生试卷：" + studentAssignment);

        if(studentAssignment1 == null) {
            studentAssignmentMapper.insert(studentAssignment);
        }
        else{
            studentAssignmentMapper.updateByImages(studentAssignment);
        }
    }

    @Override
    public TestStudentAnswerVo getTestPaperStudent(TestPaperImageDto testPaperImageDto) {
        TestStudentAnswerVo studentAnswerVo = new TestStudentAnswerVo();

        StudentAssignment studentTest = studentAssignmentMapper.selectByImages(testPaperImageDto.getTestId(), JSON.toJSONString(testPaperImageDto.getImages()));

        if(studentTest != null) {
            studentAnswerVo.setStudentScore(studentTest.getStudentScore());
            studentAnswerVo.setTitle(studentTest.getTitle());
            studentAnswerVo.setContent(studentTest.getContent());

            Student student = studentMapper.selectById(studentTest.getStudentId());

            studentAnswerVo.setSno(student.getSno());
            studentAnswerVo.setName(student.getName());
            studentAnswerVo.setClassName(student.getClassName());
        }else{
            return null;
        }
        return studentAnswerVo;
    }
}




