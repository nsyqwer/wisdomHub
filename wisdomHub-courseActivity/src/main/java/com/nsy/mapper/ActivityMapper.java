package com.nsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.pojo.Activity;
import com.nsy.model.vo.ActivityMessageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86155
* @description 针对表【activity(活动)】的数据库操作Mapper
* @createDate 2024-06-13 19:49:07
* @Entity com.nsy.pojo.Activity
*/
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

    @Select("select id, activity_type, title, begin_time, end_time from activity where course_id = #{courseId} and class_id = #{classId}")
    List<ActivityMessageVo> getAllMessage(Integer courseId, Integer classId);
}




