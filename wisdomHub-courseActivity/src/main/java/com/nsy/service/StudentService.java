package com.nsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.model.pojo.Student;

import java.util.List;

/**
* @author 86155
* @description 针对表【student】的数据库操作Service
* @createDate 2024-06-13 20:16:39
*/
public interface StudentService extends IService<Student> {
    List<Student> getByClassId(Integer classId);
}
