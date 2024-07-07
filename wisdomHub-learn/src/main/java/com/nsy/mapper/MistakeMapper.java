package com.nsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.pojo.Mistake;
import com.nsy.model.vo.MistakeDetailVO;
import com.nsy.model.vo.MistakeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【mistake】的数据库操作Mapper
* @createDate 2024-07-03 10:12:57
* @Entity com.nsy.model.pojo.Mistake
*/
@Mapper
public interface MistakeMapper extends BaseMapper<Mistake> {
    @Select("select question.id,course_name,type,title " +
            "from mistake join question  on mistake.question_id = question.id " +
            "where student_id =#{studentId} and mistake.course_id =#{courseId} ")
    List<MistakeVo> getMistakeVo(int studentId,int courseId);

    @Select("select type,title,answer,answer_analysis,student_answer,mistake.course_name " +
            "from mistake join question  on mistake.question_id = question.id " +
            "where mistake.id =#{mistakeId}")
    MistakeDetailVO getDetail(int mistakeId);


    @Select("select question.id,course_name,type,title " +
            "from mistake join question  on mistake.question_id = question.id " +
            "where student_id =#{studentId}")
    List<MistakeVo> getMistakeVoBySid(int studentId);



}




