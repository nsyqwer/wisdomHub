package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.mapper.QuestionMapper;
import com.nsy.model.pojo.Question;
import com.nsy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 宁舒意
* @description 针对表【question】的数据库操作Service实现
* @createDate 2024-05-20 14:57:56
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;




}




