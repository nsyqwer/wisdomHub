package com.nsy;



import com.nsy.mapper.StudentAssignmentMapper;
import com.nsy.model.BaseResult;
import com.nsy.service.AIDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class WisdomHubLearnApplicationTests {

    @DubboReference
    private AIDubboService aiDubboService;
@Test
void s() throws IOException {
    String a="在计算机科学的浩瀚领域中，探索计算机组成原理如同揭开一台精密机械内部运作的神秘面纱。这一探索之旅始于对数字电路与逻辑设计的深入理解，它构建了计算机运行的基础框架。从简单的布尔代数到复杂的门电路和触发器，这些看似微小的电子元件，在精心编排下，能够执行复杂的逻辑运算，为计算机的诞生奠定了基础随着技术的演进，计算机硬件的发展历史如同一部波澜壮阔的史诗，从最初的电子管计算机，到晶体管时代，再到如今的集成电路和微处理器时代，每一次技术的飞跃都深刻地改变了人类社会的面貌。这些历史背景不仅让我们看到计算机技术的快速发展，也让我们理解到，每一次进步都是对前人智慧的继承与创新。";
    aiDubboService.createKnowledgeGraph(a,1);

}



}
