package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.mapper.ClassMapper;
import com.nsy.model.pojo.Class;
import com.nsy.service.ClassService;
import org.springframework.stereotype.Service;

/**
 * @className: ClassServiceImpl
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/5 11:37
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {
}
