package com.nsy.model.neo4j.relation;


import com.nsy.model.neo4j.node.KnowledgePointNode;
import lombok.Data;
import org.neo4j.ogm.annotation.*;


/**
 * @className: PreRelation
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/8 12:26
 */
@RelationshipEntity(type="前置")
@Data
public class PreRelation {
    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private KnowledgePointNode startKnowledge;

    //结束节点
    @EndNode
    private KnowledgePointNode endKnowledge;



}
