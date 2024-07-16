package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.mapper.StudentMapper;
import com.nsy.model.pojo.Student;
import com.nsy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86155
* @description 针对表【student】的数据库操作Service实现
* @createDate 2024-06-13 20:16:39
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{
    @Autowired
    StudentMapper studentMapper;

    @Override
    public List<Student> getByClassId(Integer classId) {
        List<Student> students = studentMapper.selectByClassId(classId);
        return students;
    }

    @Override
    public Integer getIdBySnoAndName(String number, String name) {
        return studentMapper.selectIdBySnoAndName(number, name);
    }
}




