package com.nsy.manager.neo4j.repository;

import com.nsy.manager.neo4j.node.KnowledgePointNode;
import com.nsy.manager.neo4j.relation.KnowledgeRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RelationRepository extends Neo4jRepository<KnowledgeRelation,Long> {
}
