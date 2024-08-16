package com.nsy.mapper;

import com.nsy.model.dto.ClassScore;
import com.nsy.model.pojo.Assignment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.vo.ExamHistoryVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【assignment】的数据库操作Mapper
* @createDate 2024-05-20 20:36:55
* @Entity com.nsy.model.pojo.Assignment
*/
@Mapper
public interface AssignmentMapper extends BaseMapper<Assignment> {

    @Select("select id,student_score,title\n" +
            "from student_assignment\n" +
            "where student_id =#{studentId}\n" +
            "and course_id =#{courseId} " +
            "and type =2 and state=2")
   @Result(property = "examId",column = "id")
   @Result(property = "examTitle",column = "title")
    List<ExamHistoryVo> listExam(Integer studentId,Integer courseId);



}





