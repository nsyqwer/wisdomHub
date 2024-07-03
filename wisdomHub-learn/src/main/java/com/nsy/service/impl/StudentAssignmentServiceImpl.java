package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.pojo.StudentAssignment;
import com.nsy.service.StudentAssignmentService;
import com.nsy.mapper.StudentAssignmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Integer> findStudentIdsByClassIds(List<Integer> classIds) {
        // 将 List<Integer> 转换为逗号分隔的字符串
        String classIdsStr = String.join(",", classIds.stream().map(String::valueOf).toArray(String[]::new));
        return studentAssignmentMapper.findStudentIdsByClassIds(classIdsStr);
    }
}




