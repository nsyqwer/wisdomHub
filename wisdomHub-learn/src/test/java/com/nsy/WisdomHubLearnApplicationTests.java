package com.nsy;


import com.nsy.manager.neo4j.service.KnowledgeService;
import com.nsy.mapper.StudentAssignmentMapper;
import com.nsy.model.vo.MyAssignmentVO;
import com.nsy.service.StudentAssignmentService;
import com.nsy.service.impl.StudentAssignmentServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class WisdomHubLearnApplicationTests {



    @Autowired
    private  StudentAssignmentMapper studentAssignmentMapper;

    @Autowired
    KnowledgeService knowledgeService;

    @Test
    void  getAll () throws IOException {
        knowledgeService.saveKnowledgeFromJson("[{\"node_1\": \"数字电路与逻辑设计\", \"edge\": \"前置\", \"node_2\": \"计算机组成原理\"}, {\"node_1\": \"数字电路与逻辑设计\", \"edge\": \"后置\", \"node_2\": \"门电路和触发器\"}, {\"node_1\": \"门电路和触发器\", \"edge\": \"前置\", \"node_2\": \"电子元件执行逻辑运算\"}, {\"node_1\": \"电子元件执行逻辑运算\", \"edge\": \"前置\", \"node_2\": \"计算机的诞生\"}, {\"node_1\": \"计算机硬件的发展历史\", \"edge\": \"关联\", \"node_2\": \"技术演进\"}, {\"node_1\": \"冯·诺依曼体系结构\", \"edge\": \"后置\", \"node_2\": \"现代计算机的设计\"}, {\"node_1\": \"存储程序的思想\", \"edge\": \"前置\", \"node_2\": \"五大基本组成部件\"}, {\"node_1\": \"五大基本组成部件\", \"edge\": \"前置\", \"node_2\": \"计算机体系结构的基础\"}, {\"node_1\": \"CPU\", \"edge\": \"关联\", \"node_2\": \"晶体管组成的复杂电路\"}, {\"node_1\": \"存储器系统\", \"edge\": \"关联\", \"node_2\": \"计算机的存储体系\"}, {\"node_1\": \"输入输出系统\", \"edge\": \"关联\", \"node_2\": \"计算机与外界沟通的桥梁\"}, {\"node_1\": \"总线与接口技术\", \"edge\": \"关联\", \"node_2\": \"计算机内部的数据传输和通信\"}]");
    }



}
