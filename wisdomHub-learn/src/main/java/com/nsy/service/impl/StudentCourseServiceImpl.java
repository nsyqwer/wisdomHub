package com.nsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nsy.mapper.CourseMapper;
import com.nsy.mapper.StudentMapper;
import com.nsy.model.dto.CourseSetClassDTO;
import com.nsy.model.pojo.Course;
import com.nsy.model.pojo.Student;
import com.nsy.model.pojo.StudentCourse;
import com.nsy.service.StudentCourseService;
import com.nsy.mapper.StudentCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【student_course】的数据库操作Service实现
* @createDate 2024-07-05 23:06:09
*/
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse>
    implements StudentCourseService{
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;


    /**
     * 为课程设置班级
     **/
    @Override
    public void setClass(CourseSetClassDTO courseSetClassDTO) {
        Course course = courseMapper.selectById(courseSetClassDTO.getCourseId());
        for (Integer classId : courseSetClassDTO.getClassIdList()) {
            List<Student> studentList =studentMapper.selectList(new QueryWrapper<Student>().eq("class_id",classId));
            for (Student student : studentList) {
                StudentCourse studentCourse =new StudentCourse();
                studentCourse.setStudentId(student.getId());
                studentCourse.setCourseId(course.getId());
                studentCourse.setCourseName(course.getCourseName());
                studentCourse.setCourseImage(course.getImage());
                studentCourseMapper.insert(studentCourse);
            }

        }
    }
}




