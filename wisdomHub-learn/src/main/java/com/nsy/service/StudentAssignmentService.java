package com.nsy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsy.model.BaseResult;
import com.nsy.model.dto.AssignmentPublishDTO;
import com.nsy.model.dto.CourseSetClassDTO;
import com.nsy.model.dto.StudentAssignmentDTO;
import com.nsy.model.pojo.StudentAssignment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.model.vo.StudentAssignDetailVO;
import com.nsy.model.vo.StudentSubmissionVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【student_assignment】的数据库操作Service
* @createDate 2024-05-27 08:37:50
*/
@Service
public interface StudentAssignmentService extends IService<StudentAssignment> {
    public List<Integer> findStudentIdsByClassIds(List<Integer> classIds);

    void writeAssignment(StudentAssignmentDTO studentAssignmentDTO) throws JsonProcessingException;


   List<StudentSubmissionVO> listSubmission(Integer assignmentId, Integer type, Integer studentAssignmentState);

    StudentAssignDetailVO geStuAssignDetail(Integer studentAssignmentId) throws JsonProcessingException;
}
