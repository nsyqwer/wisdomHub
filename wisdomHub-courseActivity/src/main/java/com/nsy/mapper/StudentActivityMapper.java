package com.nsy.mapper;

import com.nsy.pojo.StudentActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author 86155
* @description 针对表【student_activity(学生和活动关系表)】的数据库操作Mapper
* @createDate 2024-06-13 20:44:00
* @Entity com.nsy.pojo.StudentActivity
*/
@Mapper
public interface StudentActivityMapper extends BaseMapper<StudentActivity> {

    @Update("update student_activity set signin_status = #{signinStatus}, signin_time = #{time} where student_id = #{studentId} and activity_id = #{siginId}")
    void update(Integer studentId, Integer siginId, String signinStatus, LocalDateTime time);

    @Select("select * from student_activity where student_id = #{studentId} and activity_id = #{siginId}")
    StudentActivity select(Integer studentId, Integer siginId);

    @Select("select * from student_activity where activity_id = #{activityId}")
    List<StudentActivity> selectByActivityId(Integer activityId);
}




