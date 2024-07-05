package com.nsy.mapper.mapstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsy.model.dto.AssignmentAddDTO;
import com.nsy.model.dto.AssignmentPublishDTO;
import com.nsy.model.pojo.Assignment;
import com.nsy.model.pojo.StudentAssignment;
import com.nsy.model.vo.MyAssignmentVO;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-04T23:14:06+0800",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
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
}
