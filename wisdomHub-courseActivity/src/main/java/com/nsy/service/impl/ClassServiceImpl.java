package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.mapper.ClassMapper;
import com.nsy.model.pojo.Class;
import com.nsy.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 文旅航
 * @date 2024/7/8 21:31
 * @className ClassServiceImpl
**/

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {
    @Autowired
    ClassMapper classMapper;
    @Override
    public List<Class> getAllClass() {
        return classMapper.getAllClass();
    }

    @Override
    public List<Class> getByTeacherId(Integer teacherId) {
        return classMapper.getClassByTeacherId(teacherId);
    }
}
