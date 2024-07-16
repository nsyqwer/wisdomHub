package com.nsy.service;

import com.nsy.model.dto.SaveQuestionInfo;
import com.nsy.model.dto.SaveTestPaperDto;
import com.nsy.model.dto.TestPaperAnswer;
import com.nsy.model.pojo.TestPaper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86155
* @description 针对表【test_paper(试卷信息)】的数据库操作Service
* @createDate 2024-07-11 03:50:59
*/
public interface TestPaperService extends IService<TestPaper> {

    TestPaper saveAsDraft(SaveTestPaperDto saveTestPaperDto, int state);

    TestPaper addTestPaper(Integer teacherId, Integer course);

    void saveQuestionInfo(SaveQuestionInfo saveQuestionInfo);

    SaveQuestionInfo getQuestionInfoById(Integer id);

    void saveTestPaperAnswer(TestPaperAnswer testPaperAnswer);

    TestPaperAnswer getTestPaperAnswer(Integer id);

    void finish(Integer id);

    void saveImageLists(List<List<String>> lists, Integer testId);

    List<List<String>> getImageLists(Integer testId);
}
