package com.nsy.manager.neo4j.repository;


import com.nsy.manager.neo4j.node.KnowledgePointNode;
import com.nsy.manager.neo4j.relation.KnowledgeRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KnowledgeRepository extends Neo4jRepository<KnowledgePointNode,Long> {
 
    @Query("Match (p:知识点) return p")
    List<KnowledgePointNode> findParentList();

    Optional<KnowledgePointNode> findByName(String name);
}