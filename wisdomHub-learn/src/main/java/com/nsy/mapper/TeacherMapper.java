package com.nsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 宁舒意
* @description 针对表【teacher】的数据库操作Mapper
* @createDate 2024-07-09 00:17:47
* @Entity com.nsy.model.pojo.Teacher
*/
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

}




