package com.nsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.model.dto.AssignmentQuestionDTO;
import com.nsy.model.pojo.Assignment;
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
    List<AssignmentQuestionDTO> getQuestion(String contentJson) throws IOException;

}
