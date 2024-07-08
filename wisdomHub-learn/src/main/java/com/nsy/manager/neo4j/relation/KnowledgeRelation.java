package com.nsy.manager.neo4j.relation;

import com.nsy.manager.neo4j.node.KnowledgePointNode;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

/**
 * @className: KnowledgeRelation
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/8 11:14
 */
@RelationshipEntity(type="关系")
@Data
public class KnowledgeRelation {

    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private KnowledgePointNode startKnowledge;

    //结束节点
    @EndNode
    private KnowledgePointNode endKnowledge;
    /**
     * 前置或者后置
    **/
    @Property
    private String relation;



}
