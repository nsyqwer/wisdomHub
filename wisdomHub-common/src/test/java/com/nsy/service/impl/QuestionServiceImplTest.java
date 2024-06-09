package com.nsy.service.impl;

import com.nsy.model.pojo.Question;
import com.nsy.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceImplTest {

    @Autowired
    QuestionService questionService;

    @Test
    void getById(){
        Question byId = questionService.getById(112L);
        System.out.println(byId);
    }

}