package com.nsy.mapper.mapstruct;

import com.nsy.model.dto.AddStudentDto;
import com.nsy.model.pojo.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentDtoMapstruct {

    @Mapping(target = "name", source = "name")
    Student addStudentDtoToStudent(AddStudentDto addStudentDto);
}
