package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.mapper.AssignmentMapper;
import com.nsy.dto.AssignmentQuestionDTO;
import com.nsy.pojo.Assignment;
import com.nsy.service.AssignmentService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
* @author 宁舒意
* @description 针对表【assignment】的数据库操作Service实现
* @createDate 2024-05-20 20:36:55
*/
@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment>
    implements AssignmentService {
    @Override
    public List<AssignmentQuestionDTO> getQuestion(String contentJson) throws IOException {
        //json数组转对象集合
        ObjectMapper objectMapper = new ObjectMapper();
        List<AssignmentQuestionDTO> assignmentQuestionDTOList = Arrays
                .asList(objectMapper.readValue(contentJson, AssignmentQuestionDTO[].class));
        return assignmentQuestionDTOList;
    }
}




