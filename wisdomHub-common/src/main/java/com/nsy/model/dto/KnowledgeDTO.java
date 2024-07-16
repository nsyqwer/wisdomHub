package com.nsy.model.dto;


import com.nsy.model.neo4j.node.KnowledgePointNode;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @className: KnowledgeDTO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/14 9:50
 */
@Data
public class KnowledgeDTO implements Serializable {
    private Long roodId;
    private List<KnowledgePointNode> nodes;

    private List<FromTo> lines;
}
