package com.nsy.mapper.mapstruct;

import com.nsy.model.dto.AddStudentDto;
import com.nsy.model.dto.SaveTestPaperDto;
import com.nsy.model.dto.StudentTestPaperInfo;
import com.nsy.model.dto.TestPaperStudentAnswer;
import com.nsy.model.pojo.Student;
import com.nsy.model.pojo.StudentAssignment;
import com.nsy.model.pojo.TestPaper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TestPaperDtoMapper {
    @Mapping(target = "title", source = "title")
    TestPaper saveTestPaperDtoToPojo(SaveTestPaperDto saveTestPaperDto);

    @Mapping(target = "studentScore", source = "studentScore")
    StudentAssignment saveStudentInfoToPojo(StudentTestPaperInfo studentTestPaperInfo);

    @Mappings(value = {
        @Mapping(target = "assignmentId", source = "testId"),
        @Mapping(target = "testPaperImages", ignore = true)
    })
    StudentAssignment studentAnswerToStudentAssignment(TestPaperStudentAnswer studentAnswer);
}
