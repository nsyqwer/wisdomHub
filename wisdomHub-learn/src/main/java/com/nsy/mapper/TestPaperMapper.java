package com.nsy.mapper;

import com.nsy.model.dto.SaveQuestionInfo;
import com.nsy.model.dto.TestPaperAnswer;
import com.nsy.model.pojo.TestPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
* @author 86155
* @description 针对表【test_paper(试卷信息)】的数据库操作Mapper
* @createDate 2024-07-11 03:50:59
* @Entity com.nsy.model.pojo.TestPaper
*/
@Mapper
public interface TestPaperMapper extends BaseMapper<TestPaper> {

    @Select("select * from test_paper where teacher_id = #{teacherId} and course_id = #{courseId} and state = 0")
    TestPaper selectDrafts(Integer teacherId, Integer courseId);

    @Update("update test_paper " +
            "set title = #{title}, questions_image = #{questionsImage}, questions = #{questions}, score = #{score} " +
            "where id = #{id}")
    Integer saveQuestionInfo(SaveQuestionInfo dto);

    @Select("select id, title, questions_image, questions, score " +
            "from test_paper " +
            "where id = #{id}")
    SaveQuestionInfo selectQuestionById(Integer id);

    @Update("update test_paper " +
            "set answers = #{answers}, answers_docx = #{answersDocx} " +
            "where id = #{id}")
    Integer saveTestPaperAnswer(TestPaperAnswer dto);

    @Select("select id, answers_docx, answers " +
            "from test_paper " +
            "where id = #{id}")
    TestPaperAnswer selectTestPaperAnswer(Integer id);

    @Update("update test_paper " +
            "set state = 1 " +
            "where state = 0")
    Integer finish();

    @Update("update test_paper " +
            "set test_paper_images = #{json} " +
            "where state = 0 and id = #{testId}")
    void insertImageLists(String json, Integer testId);

    @Update("update test_paper set id = #{assignmentId} where id = #{testPaperId}")
    void updateIdById(Integer testPaperId, Integer assignmentId);
}




