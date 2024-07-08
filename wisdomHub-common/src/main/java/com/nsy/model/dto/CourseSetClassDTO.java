package com.nsy.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @className: CourseSetClassDTO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/5 22:58
 */
@Data
public class CourseSetClassDTO {
    private Integer courseId;
    private List<Integer> classIdList;
}
