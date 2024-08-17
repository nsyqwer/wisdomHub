package com.nsy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.JsonObject;
import com.nsy.mapper.*;
import com.nsy.mapper.mapstruct.TestPaperDtoMapper;
import com.nsy.model.dto.SaveQuestionInfo;
import com.nsy.model.dto.SaveTestPaperDto;
import com.nsy.model.dto.StudentTestPaperInfo;
import com.nsy.model.dto.TestPaperAnswer;
import com.nsy.model.pojo.Assignment;
import com.nsy.model.pojo.Student;
import com.nsy.model.pojo.StudentAssignment;
import com.nsy.model.pojo.TestPaper;
import com.nsy.service.ClassService;
import com.nsy.service.TestPaperService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    AssignmentMapper assignmentMapper;

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
        //进行试卷数据库的状态更改
        testPaperMapper.finish();
        studentAssignmentMapper.updateStateByTest(id);

        List<StudentAssignment> studentAssignmentList = studentAssignmentMapper.selectList(new QueryWrapper<StudentAssignment>().eq("assignment_id", id));

        Set<JsonObject> classes = new HashSet<>();
        int number = studentAssignmentList.size();
        for(StudentAssignment studentAssignment: studentAssignmentList){
            Integer studentId = studentAssignment.getStudentId();
            Student student = studentMapper.selectById(studentId);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", student.getClassId());
            jsonObject.addProperty("className", student.getClassName());

            classes.add(jsonObject);
        }
        System.out.println("班级：" + classes);

        //添加到考试数据库中
        TestPaper testPaper = testPaperMapper.selectById(id);
        testPaper.setClassList(classes.toString());
        testPaper.setNumber(number);
        testPaper.setTime(LocalDateTime.now());

        testPaperMapper.updateById(testPaper);

        Assignment assignment = new Assignment();

        assignment.setCourseId(testPaper.getCourseId());
        assignment.setCourseName(testPaper.getCourseName());
        assignment.setBeginDate(testPaper.getTime());
        assignment.setEndDate(testPaper.getTime());
        assignment.setScore(new BigDecimal(testPaper.getScore()));
        assignment.setTitle(testPaper.getTitle());
        assignment.setState(2);
        assignment.setType(3);
        assignment.setExamTime(0);
        assignment.setClassList(testPaper.getClassList());
        assignment.setCreatorId(testPaper.getTeacherId());
        assignment.setCreatorName(testPaper.getTeacherName());

        assignmentMapper.insert(assignment);

        testPaperMapper.updateIdById(testPaper.getId(), assignment.getId());

        setStudentAssignment(testPaper, studentAssignmentList);
        studentAssignmentMapper.updateAssignmentIdById(testPaper.getId(), assignment.getId());
    }

    public void setStudentAssignment(TestPaper testPaper, List<StudentAssignment> studentAssignmentList){

        JSONArray questions = new JSONArray(testPaper.getQuestions());
        JSONArray answers = new JSONArray(testPaper.getAnswers());

        for(StudentAssignment studentAssignment: studentAssignmentList) {
            System.out.println("进行更改的学生信息：" + studentAssignment);
            JSONArray studentAnswers = new JSONObject(studentAssignment.getContent()).getJSONArray("studentAnswer");
            JSONArray marks = new JSONObject(studentAssignment.getContent()).getJSONArray("mark");

            List<JsonObject> questionList = new ArrayList<>();

            for (int i = 0; i < questions.length(); i++) {
                JsonObject jsonObject = new JsonObject();

                JSONObject question = questions.getJSONObject(i);
                JSONObject answer = answers.getJSONObject(i);

                try {
                    JSONObject studentAnswer = studentAnswers.getJSONObject(i);
                    jsonObject.addProperty("studentAnswer", studentAnswer.get("studentAnswer").toString());
                }
                catch (Exception e){
                    jsonObject.addProperty("studentAnswer", "");
                    System.out.println(i + " 报错信息：" + e);
                    System.out.println("报错" + studentAnswers);
                }

                try{
                    JSONObject mark = marks.getJSONObject(i);
                    jsonObject.addProperty("questionComment", mark.getString("questionComment"));
                    jsonObject.addProperty("studentScore", mark.getInt("studentScore"));
                }
                catch (Exception e){
                    jsonObject.addProperty("questionComment", "");
                    jsonObject.addProperty("studentScore", "");
                }

                jsonObject.addProperty("questionScore", question.getInt("questionScore"));
                jsonObject.addProperty("title", question.getJSONObject("title").toString());
                jsonObject.addProperty("type", question.getString("type"));

                jsonObject.addProperty("answer", answer.getString("answer"));
                jsonObject.addProperty("answerAnalysis", "");

                questionList.add(jsonObject);
            }
            System.out.println("一个学生的问题集合：" + questionList);
            studentAssignment.setContent(questionList.toString());

            studentAssignmentMapper.updateById(studentAssignment);
        }

//        return questionList.toString();
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




