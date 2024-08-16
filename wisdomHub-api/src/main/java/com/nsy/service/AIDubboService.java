package com.nsy.service;

import com.nsy.model.dto.KnowledgeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public interface AIDubboService {
    void createKnowledgeGraph(String question,Integer courseId) throws IOException;

    String createMindMap(String question);


    String correct(String question);

    String createPPT(String query) throws IOException, InterruptedException;

    String createQuestion(String material, String t, String n1, String n2, String n3);

    KnowledgeDTO getKnowledgeByCID(Integer courseId);

    String createPath(String query);

    String recommendQuestion(String json);
}
