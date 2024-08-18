package com.nsy.dubboImpl;

import com.nsy.model.dto.KnowledgeDTO;
import com.nsy.neo4j.service.KnowledgeService;
import com.nsy.service.AIDubboService;
import com.nsy.service.XfxhService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @className: AIDubboServiceImpl
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/9 16:03
 */
@Service
@DubboService
public class AIDubboServiceImpl implements AIDubboService {

    @Autowired
    XfxhService xfxhService;

    @Autowired
    KnowledgeService knowledgeService;

    @Override
    public void createKnowledgeGraph(String question,Integer courseId) throws IOException {
        String answer = xfxhService.sendQuestion(question,1,false);
        knowledgeService.saveKnowledgeFromJson(answer,courseId);
    }


    @Override
    public String createMindMap(String question) {
        String answer =xfxhService.sendQuestion(question,2,true);
        return answer;
    }

    @Override
    public String correct(String question) {
        String answer =xfxhService.sendQuestion(question,3,false);
        return answer;
    }

    @Override
    public String createPPT(String query) throws IOException, InterruptedException {
        String URL = xfxhService.createPPT(query);
        return URL;
    }

    @Override
    public String createQuestion(String material, String t, String n1, String n2, String n3) {
        String questionString =xfxhService.sendQuestion(String.format("材料：%s, 难度系数：%s, 选择题个数：%s, 填空题数：%s, 问答题数：%s", material, t, n1, n2, n3),4,false);
        return questionString;
    }

    @Override
    public KnowledgeDTO getKnowledgeByCID(Integer courseId) {
        return knowledgeService.getKnowledgeByCID(courseId);
    }

    @Override
    public String createPath(String query) {
        return xfxhService.sendQuestion(query,5,true);
    }

    @Override
    public String recommendQuestion(String json) {
        return xfxhService.sendQuestion(json,6,false);
    }
}
