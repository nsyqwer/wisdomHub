package com.nsy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nsy.model.BaseResult;
import com.nsy.model.pojo.Mistake;
import com.nsy.model.pojo.Question;
import com.nsy.model.vo.MistakeDetailVO;
import com.nsy.model.vo.MistakeVo;
import com.nsy.service.MistakeService;
import com.nsy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: QuestionController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/6/10 21:04
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    /**
     * 题目服务
    **/
    @Autowired
    private QuestionService questionService;

    @Autowired
    private MistakeService mistakeService;

    /**
     * 教师：查看该课程所有题目
     * @author 宁舒意
     * @date 8:32 2024/5/27
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Question>>
     **/
    @GetMapping("/all/{courseId}")
    public BaseResult<List<Question>> allQuestion(@PathVariable int courseId){
        List<Question> questionList =questionService.list(new QueryWrapper<Question>()
                .eq("course_id",courseId));
        return new BaseResult<>(200,"获取课程所有题目",questionList);
    }

    //添加题目，删除题目，查看题目详情，导入题目（AI）
    /**
     * 教师：添加题目
     * @author 宁舒意
     * @date 19:24 2024/5/19
     * @param questions List<Question> questions，泛型为Question的集合
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("")
    public BaseResult questions(@RequestBody List<Question> questions){
        //需要前端传过来的question的connect字段本身就是Json字符串
        for (Question question : questions) {
            questionService.save(question);
        }
        return new BaseResult(200,"添加成功");
    }

    /**
     * 教师：修改题目
     * @author 宁舒意
     * @date 19:24 2024/5/19
     * @param question  题目实体
     * @return com.nsy.model.BaseResult
     **/
    @PutMapping("/mix-up")
    public BaseResult questions(@RequestBody Question question){
        questionService.updateById(question);
        return new BaseResult(200,"修改成功");
    }


    /**
     * 教师：删除题目
     * @author 宁舒意
     * @date 19:25 2024/5/19
     * @param questionId 题目id
     * @return com.nsy.model.BaseResult
     **/
    @DeleteMapping("/{questionId}")
    public BaseResult question(@PathVariable int questionId){
        questionService.removeById(questionId);
        return new BaseResult(200,"删除成功");
    }

    //TODO 导入题目（AI）

    /**
     * 教师：题目详情
     * @author 宁舒意
     * @date 18:49 2024/7/5
     * @param questionId 题目id
     * @return com.nsy.model.BaseResult<com.nsy.model.pojo.Question>
     */
    @GetMapping("/{questionId}")
    public BaseResult<Question> getQuestion(@PathVariable Integer questionId){
        Question question =questionService.getById(questionId);
        return new BaseResult(200,"获取成功",question);
    }


    /**
     * 学生：查看该课程错题集
     * @author 宁舒意
     * @date 11:33 2024/5/17
     * @param studentId 学生id
     * @param courseId  课程id
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.MistakeVo>
     **/
    @GetMapping("/mistakes")
    public BaseResult<List<MistakeVo>> mistakes(@RequestParam int studentId, @RequestParam int courseId){
        List<MistakeVo> mistakeVoList = mistakeService.listBySidCid(studentId,courseId);
        return new BaseResult(200,"获取该课程错题集成功",mistakeVoList);
    }


    /**
     * 学生：查看该学生所有课程错题集
     * @author 宁舒意
     * @date 0:08 2024/5/26
     * @param studentId 学生id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.vo.MistakeVo>>
     **/
    @GetMapping("/mistakes-all")
    public BaseResult<List<MistakeVo>> mistakeAll(@RequestParam int studentId){
        List<MistakeVo> mistakeVoList = mistakeService.listBySid(studentId);
        return new BaseResult(200,"获取所有课程错题集成功",mistakeVoList);
    }



    /**
     * 学生：查看错题详情
     * @author 宁舒意
     * @date 15:49 2024/5/17
     * @param mistakeId 错题id
     * @return com.nsy.model.BaseResult<com.nsy.model.vo.MistakeDetailVO>
     **/
    @GetMapping("/mistake-detail/{mistakeId}")
    public BaseResult<MistakeDetailVO> mistakeDetail(@PathVariable int mistakeId){
        MistakeDetailVO mistakeDetailVO =mistakeService.getDetail(mistakeId);
        return new BaseResult(200,"获取错题详情成功",mistakeDetailVO);
    }

}
