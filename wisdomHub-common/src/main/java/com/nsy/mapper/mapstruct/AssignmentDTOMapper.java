package com.nsy.mapper.mapstruct;

import com.nsy.model.dto.AssignmentAddDTO;
import com.nsy.model.dto.AssignmentPublishDTO;
import com.nsy.model.pojo.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
@Mapper
public interface AssignmentDTOMapper {
    AssignmentDTOMapper INSTANCE = Mappers.getMapper(AssignmentDTOMapper.class);
    //  Chapter toChapter(ChapterDetailDTO chapterAddDTO);

    void AddDTOtoAssignment(AssignmentAddDTO assignmentAddDTO, @MappingTarget Assignment assignment);

    void PublishDTOtoAssignment(AssignmentPublishDTO assignmentPublishDTO, @MappingTarget Assignment assignment);

}
