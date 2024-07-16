package com.nsy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.mapper.CourseMapper;
import com.nsy.mapper.StudentAssignmentMapper;
import com.nsy.mapper.TeacherMapper;
import com.nsy.mapper.TestPaperMapper;
import com.nsy.mapper.mapstruct.TestPaperDtoMapper;
import com.nsy.model.dto.SaveQuestionInfo;
import com.nsy.model.dto.SaveTestPaperDto;
import com.nsy.model.dto.StudentTestPaperInfo;
import com.nsy.model.dto.TestPaperAnswer;
import com.nsy.model.pojo.TestPaper;
import com.nsy.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author 86155
* @description 针对表【test_paper(试卷信息)】的数据库操作Service实现
* @createDate 2024-07-11 03:50:59
*/
@Service
public class TestPaperServiceImpl extends ServiceImpl<TestPaperMapper, TestPaper>
    implements TestPaperService{

    @Autowired
    TestPaperDtoMapper testPaperDtoMapper;
    @Autowired
    TestPaperMapper testPaperMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    StudentAssignmentMapper studentAssignmentMapper;

    @Override
    public TestPaper saveAsDraft(SaveTestPaperDto saveTestPaperDto, int state) {
        TestPaper testPaper = testPaperDtoMapper.saveTestPaperDtoToPojo(saveTestPaperDto);

        testPaper.setTeacherName(teacherMapper.selectById(testPaper.getTeacherId()).getName());
        testPaper.setCourseName(courseMapper.selectById(testPaper.getCourseId()).getCourseName());
        testPaper.setState(state);

        //获取参与班级集合以及总人数
        List<StudentTestPaperInfo> studentTestPapers = saveTestPaperDto.getStudentTestPapers();
        if(studentTestPapers != null){
            StringBuilder classList = new StringBuilder();
            Set<String> set = new HashSet<>();
            for(StudentTestPaperInfo studentTestPaper : studentTestPapers){
                set.add(studentTestPaper.getClassName());
            }
            for(String s : set){
                classList.append(s).append(", ");
            }
            // 如果 set 不为空，移除最后的 ", "
            if (!set.isEmpty()) {
                classList.setLength(classList.length() - 2);
            }
            testPaper.setClassList(classList.toString());
            testPaper.setNumber(studentTestPapers.size());
        }

        if(testPaper.getId() != null) {
            //进行保存，如果没有则进行添加
            TestPaper testPaper1 = testPaperMapper.selectById(testPaper.getId());
            if (testPaper1 == null) {
                testPaperMapper.insert(testPaper);
            } else {
                testPaperMapper.updateById(testPaper);
            }
        }

        return testPaper;
    }

    @Override
    public TestPaper addTestPaper(Integer teacherId, Integer courseId) {
        TestPaper testPaper = testPaperMapper.selectDrafts(teacherId, courseId);

        if(testPaper == null) {
            testPaper = new TestPaper();
            testPaper.setTeacherId(teacherId);
            testPaper.setCourseId(courseId);
            testPaper.setTeacherName(teacherMapper.selectById(testPaper.getTeacherId()).getName());
            testPaper.setCourseName(courseMapper.selectById(testPaper.getCourseId()).getCourseName());
            testPaper.setState(0);
            testPaperMapper.insert(testPaper);
        }
        return testPaper;
    }

    @Override
    public void saveQuestionInfo(SaveQuestionInfo saveQuestionInfo) {
        testPaperMapper.saveQuestionInfo(saveQuestionInfo);
    }

    @Override
    public SaveQuestionInfo getQuestionInfoById(Integer id) {
        return testPaperMapper.selectQuestionById(id);
    }

    @Override
    public void saveTestPaperAnswer(TestPaperAnswer testPaperAnswer) {
        testPaperMapper.saveTestPaperAnswer(testPaperAnswer);
    }

    @Override
    public TestPaperAnswer getTestPaperAnswer(Integer id) {
        return testPaperMapper.selectTestPaperAnswer(id);
    }

    @Override
    public void finish(Integer id) {
        testPaperMapper.finish();
        studentAssignmentMapper.updateStateByTest(id);
    }

    @Override
    public void saveImageLists(List<List<String>> lists, Integer testId) {
        testPaperMapper.insertImageLists(JSON.toJSONString(lists), testId);
    }

    @Override
    public List<List<String>> getImageLists(Integer testId) {
        TestPaper testPaper = testPaperMapper.selectById(testId);
        String images = testPaper.getTestPaperImages();
        // 使用TypeReference来指定List<List<String>>类型
        TypeReference<List<List<String>>> typeRef = new TypeReference<List<List<String>>>() {};
        List<List<String>> lists = JSON.parseObject(images, typeRef);

        System.out.println(lists);
        return lists;
    }
}




