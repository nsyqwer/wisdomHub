package com.nsy.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

public interface AIDubboService {
    void createKnowledgeGraph(String question) throws IOException;


    String createMindMap(String question);
}
