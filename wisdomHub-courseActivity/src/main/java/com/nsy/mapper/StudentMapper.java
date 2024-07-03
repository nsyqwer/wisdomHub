package com.nsy.mapper;

import com.nsy.pojo.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86155
* @description 针对表【student】的数据库操作Mapper
* @createDate 2024-06-13 20:16:39
* @Entity com.nsy.pojo.Student
*/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select * from student where class_id = #{classId}")
    List<Student> selectByClassId(Integer classId);
}




