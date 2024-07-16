package com.nsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.constant.AssignmentStateEnum;
import com.nsy.constant.AssignmentTypeEnum;
import com.nsy.constant.QueryAssignmentEnum;
import com.nsy.constant.StudentAssignmentEnum;
import com.nsy.mapper.*;
import com.nsy.mapper.mapstruct.AssignmentDTOMapper;
import com.nsy.model.dto.*;
import com.nsy.model.pojo.*;
import com.nsy.model.pojo.Class;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.model.vo.TeacherAssignVO;
import com.nsy.model.vo.TeacherExamVO;
import com.nsy.service.AIDubboService;
import com.nsy.service.AssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
* @author 宁舒意
* @description 针对表【assignment】的数据库操作Service实现
* @createDate 2024-05-20 20:36:55
*/
@Slf4j
@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment>
    implements AssignmentService {


    @Autowired
    private StudentAssignmentMapper studentAssignmentMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @DubboReference
    private AIDubboService aiDubboService;

    @Override
    public List<AssignmentQuestionDTO> getQuestion(String contentJson) throws IOException {
        //json数组转对象集合
        ObjectMapper objectMapper = new ObjectMapper();
        List<AssignmentQuestionDTO> assignmentQuestionDTOList = Arrays
                .asList(objectMapper.readValue(contentJson, AssignmentQuestionDTO[].class));
        return assignmentQuestionDTOList;
    }

    @Override
    public String ListToJson(List<AssignmentQuestionDTO> assignmentQuestionDTOList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return  objectMapper.writeValueAsString(assignmentQuestionDTOList);
    }

    @Override
    public List<MyAssignmentVO> listToVO(int studentId, int courseId,  int operation,int type) {
        List<MyAssignmentVO> myAssignmentVOList =null;
        if(Objects.equals(operation, QueryAssignmentEnum.UnFinished.getCode())){
            myAssignmentVOList =studentAssignmentMapper.listUnFinished(studentId,courseId,type);
        }else if(Objects.equals(operation,QueryAssignmentEnum.Finished.getCode())){
           myAssignmentVOList =studentAssignmentMapper.listFinished(studentId,courseId,type);
        }else if(Objects.equals(operation,QueryAssignmentEnum.All.getCode())){
            myAssignmentVOList =studentAssignmentMapper.listAll(studentId,courseId,type);
        }
        return myAssignmentVOList;
    }


    @Override
    public void publishAssignment(AssignmentPublishDTO assignmentPublishDTO) throws IOException {
        //查询到作业，将起始时间和截止时间赋值
        Assignment assignment =assignmentMapper.selectById(assignmentPublishDTO.getAssignmentId());
        assignment.setBeginDate(assignmentPublishDTO.getBeginDate());
        assignment.setEndDate(assignmentPublishDTO.getEndDate());
        int type =assignmentPublishDTO.getType();
        if(Objects.equals(type, AssignmentTypeEnum.EXAM.getCode()))
        {
            assignment.setExamTime(assignmentPublishDTO.getExamTime());
        }

        //根据id数组得到班级集合
        List<Class> classList =new ArrayList<>();
        for (Integer id : assignmentPublishDTO.getClassIdList()) {
            Class c = classMapper.selectById(id);
            classList.add(c);
        }

        //班级集合转换json
        ObjectMapper objectMapper = new ObjectMapper();
        String classListJson =objectMapper.writeValueAsString(classList);

        //将作业状态改为1（进行中）,并且把发放的班级列表改成现在选择的
        assignment.setState(1);
        assignment.setClassList(classListJson);
        assignmentMapper.updateById(assignment);
        //将每个学生都加入到作业中来
        String classIdsStr = String.join(",", assignmentPublishDTO.getClassIdList().stream().map(String::valueOf).toArray(String[]::new));
        List<Integer> studentIds =studentAssignmentMapper.findStudentIdsByClassIds(classIdsStr);

        //查询到作业所属课程id
        //这里的content是assignmentQuestion的数组，发布的时候答案和答案解析不能传过去
        List<AssignmentQuestionDTO> assignmentQuestionDTOList = getQuestion(assignment.getContent());

//        assignmentQuestionDTOList.forEach(dto -> {
//            dto.setAnswer(null);
//            dto.setAnswerAnalysis(null);
//        });
        String json = ListToJson(assignmentQuestionDTOList);

        for (Integer studentId : studentIds) {
            StudentAssignment studentAssignment =new StudentAssignment();
            studentAssignment.setAssignmentId(assignment.getId());
            studentAssignment.setState(0);//0表示未完成
            studentAssignment.setStudentScore(BigDecimal.ZERO);
            studentAssignment.setCourseId(assignment.getCourseId());
            studentAssignment.setTitle(assignment.getTitle());
            studentAssignment.setType(type);
            studentAssignment.setStudentId(studentId);
            studentAssignment.setContent(json);
            studentAssignmentMapper.insert(studentAssignment);
        }
    }


    @Override
    public List<TeacherAssignVO> getTeacherAssignVO(TeacherAssignmentDTO teaCherAssignmentDTO) {
        int state =teaCherAssignmentDTO.getState();
       List<Assignment>assignmentList =  assignmentMapper.selectList(new QueryWrapper<Assignment>()
               .eq("course_id",teaCherAssignmentDTO.getCourseId())
               .eq("state",state)
               .eq("type",teaCherAssignmentDTO.getType()));

        int courseId =teaCherAssignmentDTO.getCourseId();



       List<TeacherAssignVO> teacherAssignVOList=new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            TeacherAssignVO teacherAssignVO =new TeacherAssignVO();
            AssignmentDTOMapper.INSTANCE.assignPojoToVo(assignment,teacherAssignVO);

            int unCommittedNum =0;
            int waitCorrectNum=0;
            int finishedNum=0;
            int allNum=0;
            if(state==1||state==2){
                //遍历每个班级
                for (Class aClass : teacherAssignVO.getClassList()) {
                    unCommittedNum+=studentAssignmentMapper.countTeaAssign(aClass.getId(),courseId, state, StudentAssignmentEnum.UNCOMMITTED.getCode());
                    waitCorrectNum+=studentAssignmentMapper.countTeaAssign(aClass.getId(), courseId, state, StudentAssignmentEnum.WaitCorrect.getCode());
                    finishedNum+=studentAssignmentMapper.countTeaAssign(aClass.getId(), courseId, state, StudentAssignmentEnum.FINISHED.getCode());
                }
                allNum =unCommittedNum+waitCorrectNum+finishedNum;
            }


            teacherAssignVO.setUnCommittedNum(unCommittedNum);
            teacherAssignVO.setWaitCorrectNum(waitCorrectNum);
            teacherAssignVO.setFinishedNum(finishedNum);
            teacherAssignVO.setAllNum(allNum);

            teacherAssignVOList.add(teacherAssignVO);
        }

        return teacherAssignVOList;
    }

    @Override
    public Integer saveAssignAndQuestion(AssignmentAddDTO assignmentAddDTO) throws JsonProcessingException {
                //state设置为0表示草稿
        Teacher teacher =teacherMapper.selectById(assignmentAddDTO.getCreatorId());
        Assignment assignment =new Assignment();
        if (assignmentAddDTO.getAssignmentId() != null) {
            //表示编辑
         assignment =assignmentMapper.selectById(assignmentAddDTO.getAssignmentId());
        }
        AssignmentDTOMapper.INSTANCE.AddDTOtoAssignment(assignmentAddDTO,assignment);

        assignment.setState(0);
        Course course=courseMapper.selectById(assignmentAddDTO.getCourseId());
        assignment.setCourseName(course.getCourseName());
        BigDecimal totalScore = BigDecimal.ZERO; // 初始化总和为0


        for (AssignmentQuestionDTO dto : assignmentAddDTO.getContent()) {
            totalScore = totalScore.add(dto.getQuestionScore()); // 将每个对象的 questionScore 字段值加到总和中
            Question question =new Question();
            AssignmentDTOMapper.INSTANCE.AddDTOtoQuestion(dto,question);
            question.setCourseId(course.getId());
            question.setCourseName(course.getCourseName());
            question.setCreatorId(teacher.getId());
            question.setCreatorName(teacher.getName());
            questionMapper.insert(question);
        }
        assignment.setScore(totalScore);
        assignment.setCreatorId(teacher.getId());
        assignment.setCreatorName(teacher.getName());
        assignmentMapper.insert(assignment);
        return assignment.getId();
    }

    @Override
    public List<TeacherExamVO> listExamByTid(Integer teacherId,Integer state) {
        List<Assignment> assignmentList =new ArrayList<>();
        if(state==null){
            assignmentList =assignmentMapper.selectList(new QueryWrapper<Assignment>()
                    .eq("creator_id",teacherId).eq("type",2));
        }
        else{
            assignmentList =assignmentMapper.selectList(new QueryWrapper<Assignment>()
                    .eq("creator_id",teacherId).eq("type",2).eq("state",state));
        }
        List<TeacherExamVO> teacherExamVOList =new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            TeacherExamVO teacherExamVO =new TeacherExamVO();
            AssignmentDTOMapper.INSTANCE.assignToExamVO(assignment,teacherExamVO);
            teacherExamVOList.add(teacherExamVO);
        }
       // AssignmentDTOMapper.INSTANCE.listAssignToExamVO(assignmentList,teacherExamVOList);


        return teacherExamVOList;
    }


    /**
     * 答案解析有可能为空
     * ai批阅完不要改状态为已批阅，因为还需要教师的二次批阅
    **/
    @Override
    public void aiCorrect(CorrectAssignmentDTO correctAssignmentDTO) throws JsonProcessingException {
        //先把作业答案和解析查到
        Integer teacherId =correctAssignmentDTO.getTeacherId();
//        Assignment assignment = assignmentMapper.selectById(correctAssignmentDTO.getAssignmentId());
        List<StudentAssignment> studentAssignmentList =studentAssignmentMapper
                .listByAIDCIDType(correctAssignmentDTO.getAssignmentId(),correctAssignmentDTO.getClassId(),
                        StudentAssignmentEnum.WaitCorrect.getCode(),AssignmentTypeEnum.EXAM.getCode());
        for (StudentAssignment studentAssignment : studentAssignmentList) {
            ObjectMapper objectMapper =new ObjectMapper();
            String answerJson =aiDubboService.correct(studentAssignment.getContent());
            studentAssignment.setContent(answerJson);
            //算总分数
            List<AssignmentQuestionDTO> assignmentQuestionDTOList =
                    Arrays.asList(objectMapper.readValue(studentAssignment.getContent(), AssignmentQuestionDTO[].class));
            BigDecimal studentTotalScore = BigDecimal.ZERO; // 初始化总和为0


            for (AssignmentQuestionDTO dto : assignmentQuestionDTOList) {
                studentTotalScore = studentTotalScore.add(dto.getStudentScore()); // 将每个对象的 学生得分字段值加到总和中
            }
            studentAssignment.setStudentScore(studentTotalScore);
            studentAssignment.setTeacherId(teacherId);
            studentAssignmentMapper.updateById(studentAssignment);
        }

    }




}




