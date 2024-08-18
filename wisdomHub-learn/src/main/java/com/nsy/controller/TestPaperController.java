package com.nsy.controller;

import com.nsy.model.BaseResult;
import com.nsy.model.dto.*;
import com.nsy.model.pojo.TestPaper;
import com.nsy.model.vo.TestStudentAnswerVo;
import com.nsy.service.StudentAssignmentService;
import com.nsy.service.TestPaperService;
import com.nsy.util.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/testPaper")
public class TestPaperController{
    /*
    1、保存评阅草稿
    2、完成阅卷，保存及更改状态
    3、上传压缩包，返回试卷分类集合
    4、获取评阅信息
    */

    @Autowired
    TestPaperService testPaperService;

    @Autowired
    StudentAssignmentService studentAssignmentService;

    /**
     * 教师：完成阅卷（更改试卷状态）
     * @author 文旅航
     * @date 2024/7/11 22:32
     * @param id 试卷id
     * @return com.nsy.model.BaseResult
    **/

    @PostMapping("finishTestPaper")
    public BaseResult finish(@RequestParam Integer id){
        log.info("完成阅卷（更改试卷状态");
        testPaperService.finish(id);
        return new BaseResult(200, "更改试卷状态成功");
    }

    /**
     * 教师：新建试卷
     * @author 文旅航
     * @date 2024/7/11 23:34
     * @param teacherId 老师id
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult<java.lang.Integer>
    **/
    @PostMapping("addTestPaper")
    public BaseResult<Integer> addTestPaper(@RequestParam Integer teacherId, @RequestParam Integer courseId){
        log.info("新建试卷成功");
        TestPaper testPaper = testPaperService.addTestPaper(teacherId, courseId);

        System.out.println("新建试卷：" + testPaper);
        return new BaseResult<>(200, "新建试卷成功", testPaper.getId());
    }

    /**
     * 教师：保存试卷题目信息
     * @author 文旅航
     * @date 2024/7/11 23:49
     * @param saveQuestionInfo
     * @return com.nsy.model.BaseResult
    **/
    @PostMapping("saveQuestionInfo")
    public BaseResult saveQuestionInfo(@RequestBody SaveQuestionInfo saveQuestionInfo){
        log.info("保存试卷题目信息");
        testPaperService.saveQuestionInfo(saveQuestionInfo);
        return new BaseResult(200, "保存试卷题目信息成功");
    }

    /**
     * 教师：获取试卷题目信息
     * @author 文旅航
     * @date 2024/7/12 0:22
     * @param id 试卷id
     * @return com.nsy.model.BaseResult<com.nsy.model.dto.SaveQuestionInfo>
    **/


    @GetMapping("getQuestionInfo")
    public BaseResult<SaveQuestionInfo> getQuestionInfo(@RequestParam Integer id){
        log.info("获取试卷题目信息");
        SaveQuestionInfo saveQuestionInfo = testPaperService.getQuestionInfoById(id);
        return new BaseResult<>(200, "获取试卷题目信息成功", saveQuestionInfo);
    }

    /**
     * 教师：保存试卷答案
     * @author 文旅航
     * @date 2024/7/12 0:23
     * @param testPaperAnswer
     * @return com.nsy.model.BaseResult
    **/

    @PostMapping("saveTestPaperAnswer")
    public BaseResult saveTestPaperAnswer(@RequestBody TestPaperAnswer testPaperAnswer){
        log.info("保存试卷答案");
        testPaperService.saveTestPaperAnswer(testPaperAnswer);
        return new BaseResult(200, "保存试卷答案信息成功");
    }

    /**
     * 教师：获取试卷答案
     * @author 文旅航
     * @date 2024/7/12 0:28
     * @param id 试卷id
     * @return com.nsy.model.BaseResult<com.nsy.model.dto.TestPaperAnswer>
    **/

    @GetMapping("getTestPaperAnswer")
    public BaseResult<TestPaperAnswer> getTestPaperAnswer(@RequestParam Integer id){
        log.info("获取试卷答案信息");
        TestPaperAnswer testPaperAnswer = testPaperService.getTestPaperAnswer(id);
        return new BaseResult<>(200, "获取试卷答案信息成功", testPaperAnswer);
    }

    /**
     * 教师：上传压缩包，获取试卷
     * @author 文旅航
     * @date 2024/7/12 22:57
     * @param file 压缩包地址
     * @param testId 试卷id
     * @return com.nsy.model.BaseResult<java.util.List<java.util.List<java.lang.String>>>
    **/


    @PostMapping("uploadFiles")
    public BaseResult<List<List<String>>> uploadFiles(@RequestParam MultipartFile file, @RequestParam Integer testId) throws IOException {
        log.info("上传学生试卷压缩包");
        if(!MyFileUtils.checkZipFileNames(file)){
            log.error("压缩包中文件格式不符合要求，请重新上传");
            return new BaseResult<>(400, "压缩包中文件格式不符合要求，请重新上传");
        }
        List<List<String>> lists = MyFileUtils.uploadUnzippedFiles(file);

        testPaperService.saveImageLists(lists, testId);
        return new BaseResult<>(200, "获取学生试卷成功", lists);
    }

    /**
     * 教师：获取所有学生试卷
     * @author 文旅航
     * @date 2024/7/12 22:55
     * @param testId
     * @return com.nsy.model.BaseResult<java.util.List<java.util.List<java.lang.String>>>
     **/


    @GetMapping("getAllTestPaper")
    public BaseResult<List<List<String>>> getAllTEstPaper(@RequestParam  Integer testId){
        log.info("获取所有学生试卷");
        return new BaseResult<>(200, "获取所有学生试卷", testPaperService.getImageLists(testId));
    }

    /**
     * 教师：保存学生作答以及批阅信息
     * @author 文旅航
     * @date 2024/7/12 22:58
     * @param studentAnswer
     * @return com.nsy.model.BaseResult
    **/


    @PostMapping("saveStudentAnswer")
    public BaseResult saveStudentAnswer(@RequestBody TestPaperStudentAnswer studentAnswer){
        log.info("保存学生评阅信息");
        studentAssignmentService.saveTestPaperStudent(studentAnswer);
        return new BaseResult(200, "保存学生评阅信息成功");
    }

    /**
     * 教师：获取学生作答以及评阅信息
     * @author 文旅航
     * @date 2024/7/12 22:58
     * @param testPaperImageDto
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.TestStudentAnswerVo>
    **/

    @PostMapping("getStudentAnswer")
    public BaseResult<TestStudentAnswerVo> getStudentAnswer(@RequestBody TestPaperImageDto testPaperImageDto){
        log.info("通过试卷获取学生答案");
        TestStudentAnswerVo studentAnswerVo = studentAssignmentService.getTestPaperStudent(testPaperImageDto);
        return new BaseResult<>(200, "获取学生评阅信息成功", studentAnswerVo);
    }
}
