package com.nsy.mapper;

import com.nsy.pojo.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 宁舒意
* @description 针对表【question】的数据库操作Mapper
* @createDate 2024-05-20 14:57:56
* @Entity com.nsy.model.pojo.Question
*/
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}




