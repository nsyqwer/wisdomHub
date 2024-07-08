package com.nsy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.model.dto.CourseSetClassDTO;
import com.nsy.model.pojo.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
* @author 宁舒意
* @description 针对表【student_course】的数据库操作Service
* @createDate 2024-07-05 23:06:09
*/
@Service
public interface StudentCourseService extends IService<StudentCourse> {


    void setClass(CourseSetClassDTO courseSetClassDTO);
}
