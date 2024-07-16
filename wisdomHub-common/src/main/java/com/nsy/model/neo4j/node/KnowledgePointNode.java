package com.nsy.model.neo4j.node;


import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;

/**
 * @className: KnowledgePointNode
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/8 10:58
 */
@Data
@NodeEntity(label = "知识点")
public class KnowledgePointNode implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Property("courseId")
    private int courseId;

}
