package com.nsy.mapper;

import com.nsy.model.pojo.StudentAssignment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.vo.MyAssignmentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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


    /**
     * 查询所有作业
     * @author 宁舒意
     * @date 21:33 2024/7/2
     * @param studentId
     * @param courseId
     * @return java.util.List<com.nsy.model.vo.MyAssignmentVO>
     */
    @Select("select assignment_id,end_date,title,student_assignment.state from student_assignment\n" +
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
    @Select("select assignment_id,end_date,title,student_assignment.state from student_assignment\n" +
            "        join assignment on student_assignment.assignment_id = assignment.id\n" +
            "        where student_id =#{studentId}\n" +
            "        and assignment.course_id  =#{courseId}" +
            "        and student_assignment.state=0 OR student_assignment.state=1 " +
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
    @Select("select assignment_id,end_date,title,student_assignment.state from student_assignment\n" +
            "        join assignment on student_assignment.assignment_id = assignment.id\n" +
            "        where student_id =#{studentId}\n" +
            "        and assignment.course_id  =#{courseId}" +
            "        and student_assignment.state=2 " +
            "        and assignment.type=#{type}")
    List<MyAssignmentVO> listUnFinished(int studentId, int courseId,int type);




    @Select("SELECT COUNT(*) " +
            "FROM student_assignment sa " +
            "JOIN assignment a ON sa.assignment_id = a.id " +
            "JOIN student s ON sa.student_id = s.id " +
            "WHERE s.class_id = #{classId} " +
            "AND a.course_id = #{courseId} " +
            "AND a.state = #{assignmentState} " +
            "AND sa.state = #{studentAssignmentState}")
    int countTeaAssign(int classId,  int courseId, int assignmentState, int studentAssignmentState);










}




