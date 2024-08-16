package com.nsy.neo4j.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsy.model.dto.FromTo;
import com.nsy.model.dto.KnowledgeDTO;

import com.nsy.model.neo4j.node.JsonNode;
import com.nsy.model.neo4j.node.KnowledgePointNode;
import com.nsy.model.neo4j.relation.KnowledgeRelation;
import com.nsy.neo4j.repository.KnowledgeRepository;
import com.nsy.neo4j.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private RelationRepository relationRepository;

    public void saveKnowledgeFromJson(String jsonInputStream,Integer courseId) throws IOException {
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
                newNode.setCourseId(courseId);
                return newNode;
            });

            // 检查 endNode 是否已存在
            Optional<KnowledgePointNode> existingEndNode = knowledgeRepository.findByName(endNodeName);
            KnowledgePointNode endNode = existingEndNode.orElseGet(() -> {
                KnowledgePointNode newNode = new KnowledgePointNode();
                newNode.setName(endNodeName);
                newNode.setCourseId(courseId);
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

            Optional<KnowledgeRelation> existingRelation = relationRepository.findByStartAndEnd(
                    startNode, endNode, node.getEdge());

            Optional<KnowledgeRelation> existingRelationTO = relationRepository.findByStartAndEnd(
                    endNode, startNode, node.getEdge());
            if (!existingRelation.isPresent()&&!existingRelationTO.isPresent()) {
                // 保存关系
                relationRepository.save(knowledgeRelation);
            }



        }
    }

    public KnowledgeDTO getKnowledgeByCID(Integer courseId) {
        List<KnowledgeRelation> knowledgeRelationList = knowledgeRepository.getKnowledgeByCID(courseId);
        KnowledgeDTO knowledgeDTO =new KnowledgeDTO();

        //判断List不为空并且第一个元素不为空
        if (!knowledgeRelationList.isEmpty() && knowledgeRelationList.get(0) != null) {
            knowledgeDTO.setRoodId(knowledgeRelationList.get(0).getStartKnowledge().getId());
            List<KnowledgePointNode> nodes =new ArrayList<>();
            List<FromTo> lines =new ArrayList<>();

            for (KnowledgeRelation knowledgeRelation : knowledgeRelationList) {
                KnowledgePointNode startNode =knowledgeRelation.getStartKnowledge();
                KnowledgePointNode endNode =knowledgeRelation.getEndKnowledge();
                if(!nodes.contains(startNode)){
                    nodes.add(startNode);
                }
                if(!nodes.contains(endNode)){
                    nodes.add(endNode);
                }

                FromTo fromTo =new FromTo();
                fromTo.setFrom(startNode.getId());
                fromTo.setTo(endNode.getId());
                fromTo.setText(knowledgeRelation.getRelation());

                lines.add(fromTo);

            }
            knowledgeDTO.setNodes(nodes);
            knowledgeDTO.setLines(lines);
            return knowledgeDTO;
        }else return knowledgeDTO;


    }
}
