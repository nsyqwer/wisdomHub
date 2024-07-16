package com.nsy.model.vo;

import com.nsy.model.pojo.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddChooserVo {
    /**
     * 选人学生集合
    **/
    List<Student> studentList;
    /**
     * 活动id
    **/
    Integer activityId;
}
