package com.nsy;

import com.nsy.mapper.StudentAssignmentMapper;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.service.StudentAssignmentService;
import com.nsy.service.impl.StudentAssignmentServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
class WisdomHubLearnApplicationTests {

    @Test
    void contextLoads() {
    }



    @Autowired
    private  StudentAssignmentMapper studentAssignmentMapper;

    @Test
    void  getAll (){
//        List<MyAssignmentVO> myAssignmentVOList =studentAssignmentMapper.listAll(1,1);
//        System.out.println(myAssignmentVOList);
    }


}
