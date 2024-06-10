package com.nsy.mapper;

import com.nsy.model.pojo.Chapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 宁舒意
* @description 针对表【chapter】的数据库操作Mapper
* @createDate 2024-05-22 21:34:08
* @Entity com.nsy.model.pojo.Chapter
*/
@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {

}




