package com.nsy.mapper;

import com.nsy.model.pojo.StudentAssignment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【student_assignment】的数据库操作Mapper
* @createDate 2024-05-27 08:37:50
* @Entity com.nsy.model.pojo.StudentAssignment
*/
@Mapper
public interface StudentAssignmentMapper extends BaseMapper<StudentAssignment> {
    @Select("SELECT student_id FROM student WHERE class_id IN (${classIds})")
    List<Integer> findStudentIdsByClassIds(@Param("classIds") String classIds);

}




