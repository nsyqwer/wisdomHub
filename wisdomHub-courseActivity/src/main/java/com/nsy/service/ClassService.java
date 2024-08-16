package com.nsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.model.pojo.Class;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassService extends IService<Class>  {
    List<Class> getAllClass();

    List<Class> getByTeacherId(Integer teacherId);
}
