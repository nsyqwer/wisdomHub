package com.nsy.neo4j.repository;


import com.nsy.model.neo4j.node.KnowledgePointNode;
import com.nsy.model.neo4j.relation.KnowledgeRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface RelationRepository extends Neo4jRepository<KnowledgeRelation,Long> {
    @Query("MATCH (start:知识点)-[r:关系]->(end:知识点) " +
            "WHERE start = $startKnowledge AND end = $endKnowledge AND r.relation = $relation " +
            "RETURN r")
    Optional<KnowledgeRelation> findByStartAndEnd(
            KnowledgePointNode startKnowledge, KnowledgePointNode endKnowledge, String relation);
}
