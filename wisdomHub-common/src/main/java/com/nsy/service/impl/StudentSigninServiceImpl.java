package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.model.pojo.StudentSignin;
import com.nsy.service.StudentSigninService;
import com.nsy.mapper.StudentSigninMapper;
import org.springframework.stereotype.Service;

/**
* @author 宁舒意
* @description 针对表【student_signin】的数据库操作Service实现
* @createDate 2024-06-04 23:22:33
*/
@Service
public class StudentSigninServiceImpl extends ServiceImpl<StudentSigninMapper, StudentSignin>
    implements StudentSigninService {

}




