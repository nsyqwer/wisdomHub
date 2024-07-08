package com.nsy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.model.pojo.Student;
import org.springframework.stereotype.Service;

/**
* @author 宁舒意
* @description 针对表【student】的数据库操作Service
* @createDate 2024-07-07 09:04:31
*/
@Service
public interface StudentService extends IService<Student> {

}
