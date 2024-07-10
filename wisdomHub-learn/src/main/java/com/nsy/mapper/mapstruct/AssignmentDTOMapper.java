package com.nsy.mapper.mapstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.constant.AssignmentStateEnum;
import com.nsy.constant.StudentAssignmentEnum;
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
import org.checkerframework.checker.units.qual.A;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Mapper
public interface AssignmentDTOMapper {
    AssignmentDTOMapper INSTANCE = Mappers.getMapper(AssignmentDTOMapper.class);

// 添加一个方法来将 List<AssignmentQuestionDTO> 转换为 String
    @Named("mapContent")
    default String mapContent(List<AssignmentQuestionDTO> content) throws JsonProcessingException {
        // 使用 JSON 序列化将 List 转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(content);
    }


    /**
     * 将状态号Code转化成枚举类里面的Message
     * @author 宁舒意
     * @date 21:55 2024/6/18
     * @param state 状态（0，1，2）
     * @return java.lang.String
     */
    @Named("mapstate")
    default String mapState(Integer state) {
        return Arrays.stream(StudentAssignmentEnum.values())
                .filter(e -> Objects.equals(state, e.getCode()))
                .findFirst()
                .map(StudentAssignmentEnum::getMessage)
                .orElse(null);
    }
    @Mapping(source = "content", target = "content", qualifiedByName = "mapContent")
    void AddDTOtoAssignment(AssignmentAddDTO assignmentAddDTO, @MappingTarget Assignment assignment) throws JsonProcessingException;

    void PublishDTOtoAssignment(AssignmentPublishDTO assignmentPublishDTO, @MappingTarget Assignment assignment);


    @Mapping(source = "state",target = "state",qualifiedByName = "mapstate")
    void SAToMAList(List<StudentAssignment> studentAssignmentList,@MappingTarget List<MyAssignmentVO> myAssignmentVOList);



    //对老师查看自己课程作业的接口

    @Named("mapClassList")
    default List<Class> mapClassList(String classListJson) throws JsonProcessingException {
        // 使用 JSON 序列化将 List 转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(classListJson, new TypeReference<List<Class>>() {});
    }

    @Mapping(source = "classList", target = "classList", qualifiedByName = "mapClassList")
    void assignPojoToVo(Assignment assignment, @MappingTarget TeacherAssignVO teacherAssignVO);




    void AddDTOtoQuestion(AssignmentQuestionDTO assignmentQuestionDTO, @MappingTarget Question question);



    /**
     * assignment状态int转String
    **/
    @Named("assignState")
    default String assignState(Integer state) {
        return Arrays.stream(AssignmentStateEnum.values())
                .filter(e -> Objects.equals(state, e.getCode()))
                .findFirst()
                .map(AssignmentStateEnum::getMessage)
                .orElse(null);
    }

    @Named("getQuestionNum")
    default Integer getQuestionNum(String questionJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<AssignmentQuestionDTO> assignmentQuestionDTOList =
                Arrays.asList(objectMapper.readValue(questionJson, AssignmentQuestionDTO[].class));
        return assignmentQuestionDTOList.size();
    }

    @Mapping(source = "id",target = "examId")
    @Mapping(source = "content", target = "questionNum", qualifiedByName = "getQuestionNum")
    @Mapping(source = "state", target = "state", qualifiedByName = "assignState")
    void assignToExamVO(Assignment assignment, @MappingTarget TeacherExamVO teacherExamVO);

    void listAssignToExamVO(List<Assignment> assignmentList,@MappingTarget List<TeacherExamVO> teacherExamVOList);




}
