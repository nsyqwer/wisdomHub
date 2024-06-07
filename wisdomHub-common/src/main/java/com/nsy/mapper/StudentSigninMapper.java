package com.nsy.mapper;

import com.nsy.model.pojo.StudentSignin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 宁舒意
* @description 针对表【student_signin】的数据库操作Mapper
* @createDate 2024-06-04 23:22:33
* @Entity com.nsy.model.pojo.StudentSignin
*/
@Mapper
public interface StudentSigninMapper extends BaseMapper<StudentSignin> {

}




