package com.nsy.manager.neo4j.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.manager.neo4j.node.JsonNode;
import com.nsy.manager.neo4j.node.KnowledgePointNode;
import com.nsy.manager.neo4j.relation.KnowledgeRelation;
import com.nsy.manager.neo4j.repository.KnowledgeRepository;
import com.nsy.manager.neo4j.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private RelationRepository relationRepository;

    public void saveKnowledgeFromJson(String jsonInputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> relations = mapper.readValue(jsonInputStream, new TypeReference<List<JsonNode>>(){});

        for (JsonNode node : relations) {
            String startNodeName = node.getNode_1();
            String endNodeName = node.getNode_2();

            // 检查 startNode 是否已存在
            Optional<KnowledgePointNode> existingStartNode = knowledgeRepository.findByName(startNodeName);
            KnowledgePointNode startNode = existingStartNode.orElseGet(() -> {
                KnowledgePointNode newNode = new KnowledgePointNode();
                newNode.setName(startNodeName);
                return newNode;
            });

            // 检查 endNode 是否已存在
            Optional<KnowledgePointNode> existingEndNode = knowledgeRepository.findByName(endNodeName);
            KnowledgePointNode endNode = existingEndNode.orElseGet(() -> {
                KnowledgePointNode newNode = new KnowledgePointNode();
                newNode.setName(endNodeName);
                return newNode;
            });

            // 创建关系
            KnowledgeRelation knowledgeRelation = new KnowledgeRelation();
            knowledgeRelation.setStartKnowledge(startNode);
            knowledgeRelation.setEndKnowledge(endNode);
            knowledgeRelation.setRelation(node.getEdge()); // 假设 getEdge() 返回 JsonNode，你需要转换为文本

            // 如果节点是新创建的，则需要保存它们
            if (!existingStartNode.isPresent()) {
                knowledgeRepository.save(startNode);
            }
            if (!existingEndNode.isPresent()) {
                knowledgeRepository.save(endNode);
            }

            // 保存关系
            relationRepository.save(knowledgeRelation);

        }
    }
}
