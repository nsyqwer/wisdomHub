package com.nsy.mapper;

import com.nsy.pojo.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【course】的数据库操作Mapper
* @createDate 2024-05-20 17:33:42
* @Entity com.nsy.model.pojo.Course
*/
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 教师：查看我教的课
     * @author 宁舒意
     * @date 20:00 2024/5/20
     * @param teacherId
     * @return java.util.List<com.nsy.model.pojo.Course>
    **/
    @Select("select * from course " +
            "where id in " +
            "(select course_id from teacher_course " +
            "where teacher_id =#{teacherId})")
    List<Course> listByTeacherId(Integer teacherId);

    @Select("select * from course " +
            "where id in " +
            "(select course_id from student_course " +
            "where student_id =#{studentId})")
    List<Course> listByStudentId(Integer studentId);

}




