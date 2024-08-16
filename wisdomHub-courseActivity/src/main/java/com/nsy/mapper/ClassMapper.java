package com.nsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.pojo.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassMapper extends BaseMapper<Class> {
    @Select("select * from class")
    List<Class> getAllClass();

    @Select("select * from class where teacher_id = #{teacherId}")
    List<Class> getClassByTeacherId(Integer teacherId);
}
