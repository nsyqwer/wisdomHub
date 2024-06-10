package com.nsy.mapper.mapstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.model.dto.AssignmentAddDTO;
import com.nsy.model.dto.AssignmentPublishDTO;
import com.nsy.model.dto.AssignmentQuestionDTO;
import com.nsy.model.pojo.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AssignmentDTOMapper {
    AssignmentDTOMapper INSTANCE = Mappers.getMapper(AssignmentDTOMapper.class);
    //  Chapter toChapter(ChapterDetailDTO chapterAddDTO);
// 添加一个方法来将 List<AssignmentQuestionDTO> 转换为 String
    @Named("mapContent")
    default String mapContent(List<AssignmentQuestionDTO> content) throws JsonProcessingException {
        // 使用 JSON 序列化将 List 转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(content);
    }
    @Mapping(source = "content", target = "content", qualifiedByName = "mapContent")
    void AddDTOtoAssignment(AssignmentAddDTO assignmentAddDTO, @MappingTarget Assignment assignment) throws JsonProcessingException;

    void PublishDTOtoAssignment(AssignmentPublishDTO assignmentPublishDTO, @MappingTarget Assignment assignment);


    // 假设 Assignment 类中有一个名为 setContent 的方法来设置 content 属性

}
