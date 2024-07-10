package com.nsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.constant.AssignmentTypeEnum;
import com.nsy.constant.StudentAssignmentEnum;
import com.nsy.constant.StudentChapterStateEnum;
import com.nsy.mapper.*;
import com.nsy.model.dto.CourseSetClassDTO;
import com.nsy.model.dto.StudentChapterRankDTO;
import com.nsy.model.pojo.*;
import com.nsy.model.vo.ExamHistoryVo;
import com.nsy.model.vo.StudyRecordVO;
import com.nsy.service.ChapterService;
import com.nsy.service.CourseService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 宁舒意
* @description 针对表【course】的数据库操作Service实现
* @createDate 2024-05-20 17:33:42
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private StudentChapterMapper studentChapterMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private StudentAssignmentMapper studentAssignmentMapper;

    public  List<Course> listByTeacherId(Integer teacherId){
        return courseMapper.listByTeacherId(teacherId);
    }

    @Override
    public List<Course> listByStudentId(int studentId) {
        return courseMapper.listByStudentId(studentId);
    }


    @Override
    public StudyRecordVO getStudentRecordVO(int studentId, int courseId) {
        StudyRecordVO studyRecordVO =new StudyRecordVO();
        //课程id为参数，并且level不是第一级的章节数量就是总的知识点数量
        Long allTaskPointNum = chapterMapper.selectCount(new QueryWrapper<Chapter>()
                .eq("course_id",courseId).ne("level", 1));
        //学生id和课id对应，并且state状态为已完成的章节数量
        Long finishTaskPointNum = studentChapterMapper.selectCount(new QueryWrapper<StudentChapter>()
                .eq("student_id",studentId).eq("course_id",courseId).eq("state", StudentChapterStateEnum.Finished.getCode()));
        //不是班级的排名，而是学生在自己班级的所有学生中的排名，
        StudentChapterRankDTO studentChapterRankDTO = studentChapterMapper.getRank(studentId,courseId);
        Long allAssignmentNum = assignmentMapper.selectCount(new QueryWrapper<Assignment>()
                .eq("course_id",courseId)
                .eq("type", AssignmentTypeEnum.ASSIGNMENT.getCode()));
        Long finishAssignmentNum  = studentAssignmentMapper.selectCount(new QueryWrapper<StudentAssignment>()
                .eq("course_id",courseId)
                .eq("type",AssignmentTypeEnum.ASSIGNMENT.getCode())
                .eq("state", StudentAssignmentEnum.FINISHED.getCode()));
        //作业平均分
        BigDecimal avgAssignmentScore =studentAssignmentMapper.getAvg(studentId,courseId,AssignmentTypeEnum.ASSIGNMENT.getCode());
        BigDecimal avgExamScore =studentAssignmentMapper.getAvg(studentId,courseId,AssignmentTypeEnum.EXAM.getCode());
        Long allExamNum = assignmentMapper.selectCount(new QueryWrapper<Assignment>()
                .eq("course_id",courseId)
                .eq("type", AssignmentTypeEnum.EXAM.getCode()));
        Long finishExamNum  = studentAssignmentMapper.selectCount(new QueryWrapper<StudentAssignment>()
                .eq("course_id",courseId)
                .eq("type",AssignmentTypeEnum.EXAM.getCode())
                .eq("state", StudentAssignmentEnum.FINISHED.getCode()));
        List<ExamHistoryVo> examHistoryVoList =assignmentMapper.listExam(studentId,courseId);

        studyRecordVO.setAllTaskPointNum(Math.toIntExact(allTaskPointNum));
        studyRecordVO.setFinishTaskPointNum(Math.toIntExact(finishTaskPointNum));
        studyRecordVO.setClassRank(studentChapterRankDTO.getRankInClass());
        studyRecordVO.setAllAssignmentNum(Math.toIntExact(allAssignmentNum));
        studyRecordVO.setFinishAssignmentNum(Math.toIntExact(finishAssignmentNum));
        studyRecordVO.setAvgAssignmentScore(avgAssignmentScore);
        studyRecordVO.setAllExamNum(Math.toIntExact(allExamNum));
        studyRecordVO.setFinishExamNum(Math.toIntExact(finishExamNum));
        studyRecordVO.setExamHistoryVoList(examHistoryVoList);
        studyRecordVO.setAvgExamScore(avgExamScore);

        return studyRecordVO ;
    }
}




