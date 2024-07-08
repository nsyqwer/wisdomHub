package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nsy.model.pojo.Student;
import com.nsy.service.StudentService;
import com.nsy.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
* @author 宁舒意
* @description 针对表【student】的数据库操作Service实现
* @createDate 2024-07-07 09:04:31
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

}




