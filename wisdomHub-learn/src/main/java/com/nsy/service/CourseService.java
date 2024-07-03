package com.nsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.pojo.Course;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【course】的数据库操作Service
* @createDate 2024-05-20 17:33:42
*/
public interface CourseService extends IService<Course> {


    /**
     * 教师：查看我教的课
     * @author 宁舒意
     * @date 19:59 2024/5/20
     * @param teacherId
     * @return java.util.List<com.nsy.model.pojo.Course>
    **/
    List<Course> listByTeacherId(Integer teacherId);
}
