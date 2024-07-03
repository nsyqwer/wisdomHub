package com.nsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.constant.AssignmentTypeEnum;
import com.nsy.constant.QueryAssignmentEnum;
import com.nsy.mapper.AssignmentMapper;
import com.nsy.mapper.StudentAssignmentMapper;
import com.nsy.model.dto.AssignmentPublishDTO;
import com.nsy.model.dto.AssignmentQuestionDTO;
import com.nsy.model.dto.StudentAssignmentDTO;
import com.nsy.model.pojo.Assignment;
import com.nsy.model.pojo.StudentAssignment;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.service.AssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
* @author 宁舒意
* @description 针对表【assignment】的数据库操作Service实现
* @createDate 2024-05-20 20:36:55
*/
@Slf4j
@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment>
    implements AssignmentService {


    @Autowired
    private StudentAssignmentMapper studentAssignmentMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Override
    public List<AssignmentQuestionDTO> getQuestion(String contentJson) throws IOException {
        //json数组转对象集合
        ObjectMapper objectMapper = new ObjectMapper();
        List<AssignmentQuestionDTO> assignmentQuestionDTOList = Arrays
                .asList(objectMapper.readValue(contentJson, AssignmentQuestionDTO[].class));
        return assignmentQuestionDTOList;
    }

    @Override
    public String ListToJson(List<AssignmentQuestionDTO> assignmentQuestionDTOList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return  objectMapper.writeValueAsString(assignmentQuestionDTOList);
    }

    @Override
    public List<MyAssignmentVO> listToVO(int studentId, int courseId,  int operation,int type) {
        List<MyAssignmentVO> myAssignmentVOList =null;
        if(Objects.equals(operation, QueryAssignmentEnum.UnFinished.getCode())){
            myAssignmentVOList =studentAssignmentMapper.listUnFinished(studentId,courseId,type);
        }else if(Objects.equals(operation,QueryAssignmentEnum.Finished.getCode())){
           myAssignmentVOList =studentAssignmentMapper.listFinished(studentId,courseId,type);
        }else if(Objects.equals(operation,QueryAssignmentEnum.All.getCode())){
            myAssignmentVOList =studentAssignmentMapper.listAll(studentId,courseId,type);
        }
        return myAssignmentVOList;
    }


    @Override
    public void publishAssignment(AssignmentPublishDTO assignmentPublishDTO) throws IOException {
        //查询到作业，将起始时间和截止时间赋值
        Assignment assignment =assignmentMapper.selectById(assignmentPublishDTO.getAssignmentId());
        assignment.setBeginDate(assignmentPublishDTO.getBeginDate());
        assignment.setEndDate(assignmentPublishDTO.getEndDate());
        int type =assignmentPublishDTO.getType();
        if(Objects.equals(type, AssignmentTypeEnum.EXAM.getCode()))
        {
            assignment.setExamTime(assignmentPublishDTO.getExamTime());
        }
        //将作业状态改为1（进行中）
        assignment.setState(1);
        assignmentMapper.updateById(assignment);
        //将每个学生都加入到作业中来
        String classIdsStr = String.join(",", assignmentPublishDTO.getClassIdList().stream().map(String::valueOf).toArray(String[]::new));
        List<Integer> studentIds =studentAssignmentMapper.findStudentIdsByClassIds(classIdsStr);
        StudentAssignment studentAssignment =new StudentAssignment();
        studentAssignment.setAssignmentId(assignment.getId());
        studentAssignment.setState(0);//0表示未完成
        studentAssignment.setStudentScore(BigDecimal.ZERO);
        studentAssignment.setCourseId(assignment.getCourseId());
        studentAssignment.setTitle(assignment.getTitle());
        studentAssignment.setType(type);
        //查询到作业所属课程id
        //TODO 这里的content是assignmentQuestion的数组，发布的时候答案和答案解析不能传过去
        List<AssignmentQuestionDTO> assignmentQuestionDTOList = getQuestion(assignment.getContent());

        assignmentQuestionDTOList.forEach(dto -> {
            dto.setAnswer(null);
            dto.setAnswerAnalysis(null);
        });
        String json = ListToJson(assignmentQuestionDTOList);
        studentAssignment.setContent(json);
        for (Integer studentId : studentIds) {
            studentAssignment.setStudentId(studentId);
            studentAssignmentMapper.insert(studentAssignment);
        }
    }
}




