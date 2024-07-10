package com.nsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.dto.StudentChapterRankDTO;
import com.nsy.model.pojo.StudentChapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
* @author 宁舒意
* @description 针对表【student_chapter】的数据库操作Mapper
* @createDate 2024-07-08 21:54:45
* @Entity com.nsy.model.pojo.StudentChapter
*/
@Mapper
public interface StudentChapterMapper extends BaseMapper<StudentChapter> {

    /**
     * 计算章节学习数排名
    **/
    @Select("WITH CompletedTasks AS (  \n" +
          "    SELECT  \n" +
          "        s.id AS studentId,  \n" +
          "        s.name AS studentName, \n" +
          "        s.class_id AS classId,  \n" +
          "        c.class_name AS className, \n" +
          "        COUNT(sc.chapter_id) AS completedTaskPoints  \n" +
          "    FROM  \n" +
          "        student_chapter sc  \n" +
          "    INNER JOIN  \n" +
          "        student s ON sc.student_id = s.id  \n" +
          "    INNER JOIN  class c ON s.class_id = c.id  \n" +
          "    WHERE  sc.state = 1   AND sc.course_id = #{courseId} \n" +
          "    GROUP BY  \n" +
          "        s.id, s.name, s.class_id, c.class_name  \n" +
          "),  \n" +
          "ClassRanks AS (  \n" +
          "    SELECT   ct.studentId,  RANK() OVER (PARTITION BY ct.classId ORDER BY ct.completedTaskPoints DESC) AS rank_in_class  \n" +
          "    FROM  CompletedTasks ct  \n" +
          ")  \n" +
          "SELECT  *  FROM  ClassRanks  \n" +
          "WHERE   studentId = #{studentId} -- 替换为实际的学生ID参数  \n" +
          "ORDER BY  rank_in_class;")
    StudentChapterRankDTO getRank(Integer studentId, Integer courseId);



}




