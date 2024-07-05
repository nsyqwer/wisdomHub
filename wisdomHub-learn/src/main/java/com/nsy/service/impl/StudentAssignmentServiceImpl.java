package com.nsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.constant.AssignmentTypeEnum;
import com.nsy.mapper.AssignmentMapper;
import com.nsy.mapper.CourseMapper;
import com.nsy.model.dto.AssignmentPublishDTO;
import com.nsy.model.dto.StudentAssignmentDTO;
import com.nsy.model.pojo.Assignment;
import com.nsy.model.pojo.Course;
import com.nsy.model.pojo.StudentAssignment;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.service.StudentAssignmentService;
import com.nsy.mapper.StudentAssignmentMapper;
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


}




