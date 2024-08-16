package com.nsy.controller;

import com.nsy.mapper.mapstruct.StudentDtoMapstruct;
import com.nsy.model.BaseResult;
import com.nsy.model.dto.AddStudentDto;
import com.nsy.model.pojo.Class;
import com.nsy.model.pojo.Student;
import com.nsy.service.ClassService;
import com.nsy.service.StudentService;
import com.nsy.util.OSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/class")
@Slf4j
public class ClassManagerController {
    /*
       1、获取班级中学生信息
       2、添加班级学生
       3、修改班级学生
       4、删除班级学生
       5、新增班级
       6、获取所有班级
       7、删除班级
    */
    @Autowired
    ClassService classService;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentDtoMapstruct studentDtoMapstruct;

    /**
     * 教师：获取班级中学生信息
     * @author 文旅航
     * @date 2024/7/8 22:22
     * @param classId
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Student>>
    **/

    @GetMapping("/students/{classId}")
    public BaseResult<List<Student>> getStudentByClassId(@PathVariable Integer classId){
        log.info("获取班级中学生信息");
        return new BaseResult<>(200, "获取班级中学生信息成功", studentService.getByClassId(classId));
    }

    /**
     * 教师：添加班级学生
     * @author 文旅航
     * @date 2024/7/8 22:23
     * @param addStudentDto
     * @return com.nsy.model.BaseResult<com.nsy.model.pojo.Student>
    **/

    @PostMapping("student")
    public BaseResult<Student> addStudent(@RequestBody AddStudentDto addStudentDto){
        if(!OSSUtils.checkImage(addStudentDto.getFaceImage())){
            return new BaseResult<>(400, "头像不存在，请重新上传");
        }
        log.info("添加班级学生");

        Student student = studentDtoMapstruct.addStudentDtoToStudent(addStudentDto);
        student.setSchoolId(1);
        student.setClassName(classService.getById(student.getClassId()).getClassName());

        studentService.save(student);

        return new BaseResult<>(200, "添加班级学生成功",student);
    }

    /**
     * 教师：修改学生信息
     * @author 文旅航
     * @date 2024/7/9 10:58
     * @param addStudentDto
     * @param studentId
     * @return com.nsy.model.BaseResult
    **/


    @PutMapping("student/{studentId}")
    public BaseResult updateStudent(@RequestBody AddStudentDto addStudentDto, @PathVariable Integer studentId){
        if(!OSSUtils.checkImage(addStudentDto.getFaceImage())){
            return new BaseResult<>(400, "头像不存在，请重新上传");
        }
        log.info("修改学生成功");

        Student student = studentDtoMapstruct.addStudentDtoToStudent(addStudentDto);
        student.setSchoolId(1);
        student.setClassName(classService.getById(student.getClassId()).getClassName());
        student.setId(studentId);

        studentService.updateById(student);

        return new BaseResult<>(200, "修改学生成功");
    }

    /**
     * 教师：删除学生信息
     * @author 文旅航
     * @date 2024/7/8 22:23
     * @param id
     * @return com.nsy.model.BaseResult
    **/

    @DeleteMapping("student/{id}")
    public BaseResult deleteStudent(@PathVariable Integer id){
        log.info("删除学生成功");
        studentService.removeById(id);

        return new BaseResult(200, "删除班级学生成功");
    }

    /**
     * 教师：新增班级
     * @author 文旅航
     * @date 2024/7/9 2:08
     * @param name 班级名字
     * @param teacherId 老师id
     * @return com.nsy.model.BaseResult<com.nsy.model.pojo.Class>
    **/


    @PostMapping("class")
    public BaseResult<Class> addClass(@RequestParam String name, @RequestParam Integer teacherId){
        log.info("新增班级");

        Class cs = new Class();
        cs.setClassName(name);
        cs.setTeacherId(teacherId);
        classService.save(cs);

        return new BaseResult<>(200, "新增班级成功", cs);
    }

    /**
     * 教师：获取所有班级
     * @author 文旅航
     * @date 2024/7/9 2:03
     * @param teacherId 老师id
     * @return com.nsy.model.BaseResult<java.util.List<com.nsy.model.pojo.Class>>
    **/

    @GetMapping("class")
    public BaseResult<List<Class>> getClassByTeacherId(@RequestParam Integer teacherId){
        log.info("教师获取所有班级");

        List<Class> classes =  classService.getByTeacherId(teacherId);
        return new BaseResult<>(200, "获取所有班级成功", classes);
    }

    /**
     * 教师：删除班级
     * @author 文旅航
     * @date 2024/7/8 22:24
     * @param id
     * @return com.nsy.model.BaseResult
    **/

    @DeleteMapping("class/{id}")
    public BaseResult deleteClass(@PathVariable Integer id){
        log.info("删除班级成功");

        classService.removeById(id);
        return new BaseResult(200, "删除班级成功");
    }

    /**
     * 教师：修改班级名字
     * @author 文旅航
     * @date 2024/7/9 2:15
     * @param name
     * @param classId
     * @return com.nsy.model.BaseResult
    **/

    @PutMapping("class")
    public BaseResult update(@RequestParam String name, @RequestParam Integer classId){
        log.info("修改班级成功");

        Class cs = new Class();
        cs.setId(classId); cs.setClassName(name);
        classService.updateById(cs);

        return new BaseResult(200, "修改班级名字成功");
    }

    /**
     * 教师：根据id获取班级信息
     * @author 文旅航
     * @date 2024/7/9 10:21
     * @param classId
     * @return com.nsy.model.BaseResult<com.nsy.model.pojo.Class>
    **/

    @GetMapping("getClass")
    public BaseResult<Class> getClassName (@RequestParam Integer classId){
        log.info("获取对应班级信息");
        return new BaseResult<>(200, "获得对应班级信息成功", classService.getById(classId));
    }
}
