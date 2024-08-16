package com.nsy.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【student】的数据库操作Mapper
* @createDate 2024-07-07 09:04:31
* @Entity generator.domain.Student
*/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select * from student where class_id = #{classId}")
    List<Student> selectByClassId(Integer classId);

    @Select("select id from student where name = #{name} and sno = #{number}")
    Integer selectIdBySnoAndName(String number, String name);
}




