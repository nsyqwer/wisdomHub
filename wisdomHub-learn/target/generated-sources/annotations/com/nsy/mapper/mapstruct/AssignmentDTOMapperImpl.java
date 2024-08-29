package com.nsy.mapper.mapstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsy.model.dto.AssignmentAddDTO;
import com.nsy.model.dto.AssignmentPublishDTO;
import com.nsy.model.dto.AssignmentQuestionDTO;
import com.nsy.model.pojo.Assignment;
import com.nsy.model.pojo.Class;
import com.nsy.model.pojo.Question;
import com.nsy.model.pojo.StudentAssignment;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.model.vo.TeacherAssignVO;
import com.nsy.model.vo.TeacherExamVO;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T22:51:40+0800",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 1.8.0_362 (BellSoft)"
)
public class AssignmentDTOMapperImpl implements AssignmentDTOMapper {

    @Override
    public void AddDTOtoAssignment(AssignmentAddDTO assignmentAddDTO, Assignment assignment) throws JsonProcessingException {
        if ( assignmentAddDTO == null ) {
            return;
        }

        assignment.setContent( mapContent( assignmentAddDTO.getContent() ) );
        assignment.setCourseId( assignmentAddDTO.getCourseId() );
        assignment.setTitle( assignmentAddDTO.getTitle() );
        assignment.setType( assignmentAddDTO.getType() );
        assignment.setCreatorId( assignmentAddDTO.getCreatorId() );
    }

    @Override
    public void PublishDTOtoAssignment(AssignmentPublishDTO assignmentPublishDTO, Assignment assignment) {
        if ( assignmentPublishDTO == null ) {
            return;
        }

        assignment.setBeginDate( assignmentPublishDTO.getBeginDate() );
        assignment.setEndDate( assignmentPublishDTO.getEndDate() );
        assignment.setType( assignmentPublishDTO.getType() );
        assignment.setExamTime( assignmentPublishDTO.getExamTime() );
    }

    @Override
    public void SAToMAList(List<StudentAssignment> studentAssignmentList, List<MyAssignmentVO> myAssignmentVOList) {
        if ( studentAssignmentList == null ) {
            return;
        }

        myAssignmentVOList.clear();
        for ( StudentAssignment studentAssignment : studentAssignmentList ) {
            myAssignmentVOList.add( studentAssignmentToMyAssignmentVO( studentAssignment ) );
        }
    }

    @Override
    public void assignPojoToVo(Assignment assignment, TeacherAssignVO teacherAssignVO) {
        if ( assignment == null ) {
            return;
        }

        try {
            if ( teacherAssignVO.getClassList() != null ) {
                List<Class> list = mapClassList( assignment.getClassList() );
                if ( list != null ) {
                    teacherAssignVO.getClassList().clear();
                    teacherAssignVO.getClassList().addAll( list );
                }
                else {
                    teacherAssignVO.setClassList( null );
                }
            }
            else {
                List<Class> list = mapClassList( assignment.getClassList() );
                if ( list != null ) {
                    teacherAssignVO.setClassList( list );
                }
            }
        }
        catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }
        teacherAssignVO.setAssignmentId( assignment.getId() );
        teacherAssignVO.setTitle( assignment.getTitle() );
        teacherAssignVO.setBeginDate( assignment.getBeginDate() );
        teacherAssignVO.setEndDate( assignment.getEndDate() );
        teacherAssignVO.setState( assignment.getState() );
    }

    @Override
    public void AddDTOtoQuestion(AssignmentQuestionDTO assignmentQuestionDTO, Question question) {
        if ( assignmentQuestionDTO == null ) {
            return;
        }

        question.setType( assignmentQuestionDTO.getType() );
        question.setTitle( assignmentQuestionDTO.getTitle() );
        question.setAnswer( assignmentQuestionDTO.getAnswer() );
        question.setAnswerAnalysis( assignmentQuestionDTO.getAnswerAnalysis() );
    }

    @Override
    public void assignToExamVO(Assignment assignment, TeacherExamVO teacherExamVO) {
        if ( assignment == null ) {
            return;
        }

        teacherExamVO.setExamId( assignment.getId() );
        try {
            teacherExamVO.setQuestionNum( getQuestionNum( assignment.getContent() ) );
        }
        catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }
        teacherExamVO.setState( assignState( assignment.getState() ) );
        teacherExamVO.setCourseId( assignment.getCourseId() );
        teacherExamVO.setCourseName( assignment.getCourseName() );
        teacherExamVO.setTitle( assignment.getTitle() );
        teacherExamVO.setCreatorName( assignment.getCreatorName() );
    }

    @Override
    public void listAssignToExamVO(List<Assignment> assignmentList, List<TeacherExamVO> teacherExamVOList) {
        if ( assignmentList == null ) {
            return;
        }

        teacherExamVOList.clear();
        for ( Assignment assignment : assignmentList ) {
            teacherExamVOList.add( assignmentToTeacherExamVO( assignment ) );
        }
    }

    protected MyAssignmentVO studentAssignmentToMyAssignmentVO(StudentAssignment studentAssignment) {
        if ( studentAssignment == null ) {
            return null;
        }

        MyAssignmentVO myAssignmentVO = new MyAssignmentVO();

        myAssignmentVO.setAssignmentId( studentAssignment.getAssignmentId() );
        myAssignmentVO.setTitle( studentAssignment.getTitle() );
        if ( studentAssignment.getState() != null ) {
            myAssignmentVO.setState( String.valueOf( studentAssignment.getState() ) );
        }

        return myAssignmentVO;
    }

    protected TeacherExamVO assignmentToTeacherExamVO(Assignment assignment) {
        if ( assignment == null ) {
            return null;
        }

        TeacherExamVO teacherExamVO = new TeacherExamVO();

        teacherExamVO.setCourseId( assignment.getCourseId() );
        teacherExamVO.setCourseName( assignment.getCourseName() );
        teacherExamVO.setTitle( assignment.getTitle() );
        if ( assignment.getState() != null ) {
            teacherExamVO.setState( String.valueOf( assignment.getState() ) );
        }
        teacherExamVO.setCreatorName( assignment.getCreatorName() );

        return teacherExamVO;
    }
}
