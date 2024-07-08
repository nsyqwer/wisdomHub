package com.nsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsy.model.dto.AssignmentPublishDTO;
import com.nsy.model.dto.AssignmentQuestionDTO;
import com.nsy.model.dto.TeacherAssignmentDTO;
import com.nsy.model.pojo.Assignment;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.model.vo.TeacherAssignVO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
* @author 宁舒意
* @description 针对表【assignment】的数据库操作Service
* @createDate 2024-05-20 20:36:55
*/
@Service
public interface AssignmentService extends IService<Assignment> {
    /***
     * 作业里面的题目json数组转AssignmentQuestionDTO对象集合
     * @author 宁舒意
     * @date 20:18 2024/7/2
     * @param contentJson
     * @return java.util.List<com.nsy.model.dto.AssignmentQuestionDTO>
     */
    List<AssignmentQuestionDTO> getQuestion(String contentJson) throws IOException;

    String ListToJson(List<AssignmentQuestionDTO> assignmentQuestionDTOList) throws JsonProcessingException;

    List<MyAssignmentVO> listToVO(int studentId, int courseId, int operation, int type);

    void publishAssignment(AssignmentPublishDTO assignmentPublishDTO) throws IOException;


    /**
     * 教师查看作业或者考试
     * @author 宁舒意
     * @date 16:05 2024/7/5
     * @param teaCherAssignmentDTO
     * @return java.util.List<com.nsy.model.vo.TeacherAssignVO>
     */
    List<TeacherAssignVO> getTeacherAssignVO(TeacherAssignmentDTO teaCherAssignmentDTO);
}
