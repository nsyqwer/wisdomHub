package com.nsy.mapper;

import com.nsy.model.pojo.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 宁舒意
* @description 针对表【resource】的数据库操作Mapper
* @createDate 2024-05-27 09:02:16
* @Entity com.nsy.model.pojo.Resource
*/
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

}




