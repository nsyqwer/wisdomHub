package com.nsy.mapper;

import com.nsy.model.dto.ClassScore;
import com.nsy.model.pojo.StudentAssignment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.model.vo.StudentSubmissionVO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 宁舒意
* @description 针对表【student_assignment】的数据库操作Mapper
* @createDate 2024-05-27 08:37:50
* @Entity com.nsy.model.pojo.StudentAssignment
*/
@Mapper
public interface StudentAssignmentMapper extends BaseMapper<StudentAssignment> {
    @Select("SELECT id FROM student WHERE class_id IN (${classIds})")
    List<Integer> findStudentIdsByClassIds(@Param("classIds") String classIds);

    @Select("select * FROM student_assignment\n" +
            "join student on student_assignment.student_id=student.id\n" +
            "where student.class_id =#{classId}\n" +
            "AND assignment_id=#{assignmentId}\n" +
            "AND state =#{state}\n" +
            "AND type =#{type}")
    List<StudentAssignment> listByAIDCIDType(@Param("assignmentId")Integer assignmentId,@Param("classId")Integer classId,
                                             @Param("state")Integer state,@Param("type")Integer type);



    /**
     * 查询所有作业
     * @author 宁舒意
     * @date 21:33 2024/7/2
     * @param studentId
     * @param courseId
     * @return java.util.List<com.nsy.model.vo.MyAssignmentVO>
     */
    @Select("select assignment_id,end_date,assignment.title,exam_time,student_assignment.state from student_assignment\n" +
            "        join assignment on student_assignment.assignment_id = assignment.id\n" +
            "        where student_id =#{studentId}\n" +
            "        and assignment.course_id  =#{courseId} " +
            "        and assignment.type=#{type}")
    List<MyAssignmentVO> listAll(int studentId, int courseId,int type);


    /**
     * 查询已完成的作业
     * @author 宁舒意
     * @date 21:33 2024/7/2
     * @param studentId
     * @param courseId
     * @return java.util.List<com.nsy.model.vo.MyAssignmentVO>
     */
    @Select("select assignment_id,end_date,assignment.title,exam_time,student_assignment.state from student_assignment\n" +
            "        join assignment on student_assignment.assignment_id = assignment.id\n" +
            "        where student_id =#{studentId}\n" +
            "        and assignment.course_id  =#{courseId}" +
            "        and student_assignment.state=2 " +
            "        and assignment.type=#{type}")
    List<MyAssignmentVO> listFinished(int studentId, int courseId,int type);


    /**
     * 查询未完成作业
     * @author 宁舒意
     * @date 21:33 2024/7/2
     * @param studentId
     * @param courseId
     * @return java.util.List<com.nsy.model.vo.MyAssignmentVO>
     */
    @Select("select assignment_id,end_date,assignment.title,exam_time,student_assignment.state from student_assignment\n" +
            "        join assignment on student_assignment.assignment_id = assignment.id\n" +
            "        where student_id =#{studentId}\n" +
            "        and assignment.course_id  =#{courseId}" +
            "        and (student_assignment.state=0 OR student_assignment.state=1) " +
            "        and assignment.type=#{type}")
    List<MyAssignmentVO> listUnFinished(int studentId, int courseId,int type);




    @Select("SELECT COUNT(*) " +
            "FROM student_assignment sa " +
            "JOIN assignment a ON sa.assignment_id = a.id " +
            "JOIN student s ON sa.student_id = s.id " +
            "WHERE s.class_id = #{classId} " +
            "AND sa.assignment_id = #{assignmentId} "+
            "AND a.course_id = #{courseId} " +
            "AND a.state = #{assignmentState} " +
            "AND sa.state = #{studentAssignmentState}")
    int countTeaAssign(int assignmentId,int classId,  int courseId, int assignmentState, int studentAssignmentState);



    /**
     * 教师查看作业考生提交列表
    **/
    @Select("SELECT student_assignment.id,student_id,student.name,sno,student_assignment.state," +
            "student_assignment.student_score,assignment.begin_date,assignment.end_date\n" +
            "           from student_assignment join student  on student_assignment.student_id = student.id\n" +
            "           join assignment  on assignment.id = student_assignment.assignment_id\n" +
            "           where student_assignment.assignment_id =#{assignmentId}\n" +
            "           and assignment.type =#{type}\n" +
            "           and student_assignment.state=#{studentAssignmentState}")
    @Result(property = "studentAssignmentId",column = "id")
    List<StudentSubmissionVO> listSubmission(Integer assignmentId,Integer type,Integer studentAssignmentState);

    /**
     * 教师查看作业考生提交列表，全部
    **/
    @Select("SELECT student_assignment.id,student_id,student.name,sno,student_assignment.state," +
            "student_assignment.student_score,assignment.begin_date,assignment.end_date\n" +
            "           from student_assignment join student  on student_assignment.student_id = student.id\n" +
            "           join assignment  on assignment.id = student_assignment.assignment_id\n" +
            "           where student_assignment.assignment_id =#{assignmentId}\n" +
            "           and assignment.type =#{type}\n")
    @Result(property = "studentAssignmentId",column = "id")
    List<StudentSubmissionVO> listSubmissionByAll(Integer assignmentId,Integer type);



    @Select(" SELECT AVG(student_score) \n" +
            "    FROM student_assignment \n" +
            "    WHERE course_id = #{courseId} \n" +
            "    AND student_id = #{studentId} " +
            "    and type =#{type}" +
            "    and state = 2")
    BigDecimal getAvg(Integer studentId, Integer courseId,Integer type);


    @Update("update student_assignment " +
            "set state = 2 " +
            "where assignment_id = #{id}")
    void updateStateByTest(Integer id);

    @Select("select * " +
            "from student_assignment " +
            "where assignment_id = #{testId} and test_paper_images = #{images}")
    StudentAssignment selectByImages(Integer testId, String images);

    @Update("update student_assignment " +
            "set student_score = #{studentScore}, content = #{content}, title = #{title}, student_id = #{studentId} " +
            "where test_paper_images = #{testPaperImages} and assignment_id = #{assignmentId}")
    void updateByImages(StudentAssignment sa);



    /**
     * 学情分析
    **/
    @Select("SELECT   \n" +
            "    class.id,   \n" +
            "    class.class_name,   \n" +
            "    AVG(sa.student_score) AS avg_score,  \n" +
            "    MIN(sa.student_score) AS min_score,  \n" +
            "    MAX(sa.student_score) AS max_score,  \n" +
            "    SUM(CASE WHEN sa.student_score >=assignment.score*0.6  THEN 1 ELSE 0 END) AS passing_students_count  \n" +
            "FROM   \n" +
            "    student_assignment sa  \n" +
            "JOIN   \n" +
            "    student ON sa.student_id = student.id   \n" +
            "JOIN   \n" +
            "    class ON student.class_id = class.id  \n" +
            "JOIN \n" +
            "\t  assignment ON sa.assignment_id = assignment.id\n" +
            "WHERE   \n" +
            "    sa.assignment_id = #{assignmentId}  \n" +
            "    AND sa.state = 2  \n" +
            "    AND sa.type = 2  \n" +
            "GROUP BY   \n" +
            "    class.id, class.class_name  \n" +
            "ORDER BY   \n" +
            "    class.id;")
    @Results({
            @Result(property = "avgScore", column = "avg_score"),
            @Result(property = "minScore", column = "min_score"),
            @Result(property = "maxScore", column = "max_score"),
    })
    List<ClassScore> getClassAvgByExamId(Integer assignmentId);



    @Update("update student_assignment " +
            "set assignment_id = #{assignmentId} " +
            "where assignment_id = #{testPaperId}")
    void updateAssignmentIdById(Integer testPaperId, Integer assignmentId);
}




