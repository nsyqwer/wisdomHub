package com.nsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.mapper.QuestionMapper;
import com.nsy.model.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 宁舒意
* @description 针对表【question】的数据库操作Service
* @createDate 2024-05-20 14:57:56
*/

public interface QuestionService extends IService<Question> {

}
