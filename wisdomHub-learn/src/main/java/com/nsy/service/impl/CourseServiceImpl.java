package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.mapper.CourseMapper;
import com.nsy.model.pojo.Course;
import com.nsy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【course】的数据库操作Service实现
* @createDate 2024-05-20 17:33:42
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    public  List<Course> listByTeacherId(Integer teacherId){
        return courseMapper.listByTeacherId(teacherId);
    }

    @Override
    public List<Course> listByStudentId(int studentId) {
        return courseMapper.listByStudentId(studentId);
    }
}




