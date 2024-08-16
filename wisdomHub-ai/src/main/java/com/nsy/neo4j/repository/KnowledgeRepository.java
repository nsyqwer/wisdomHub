package com.nsy.neo4j.repository;



import com.nsy.model.neo4j.node.KnowledgePointNode;
import com.nsy.model.neo4j.relation.KnowledgeRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KnowledgeRepository extends Neo4jRepository<KnowledgePointNode,Long> {
 
    @Query("Match (p:知识点) return p")
    List<KnowledgePointNode> findParentList();

    Optional<KnowledgePointNode> findByName(String name);

    /**
     * 查询所有节点标签为“知识点“及其之间的关系为“关系”的所有数据
    **/
    @Query("MATCH (k1:知识点)-[r:关系]->(k2:知识点) RETURN k1, r, k2")
    List<KnowledgeRelation> getKnowledgePointsAndRelationships();


    @Query("MATCH (k1:知识点)-[r:关系 {relation: '前置'}]->(k2:知识点) RETURN k1, r, k2")
    List<KnowledgeRelation> getPre();


    /**
     * 根据courseId查询所有节点标签为“知识点“及其之间的关系为“关系”的所有数据
     **/
    @Query("MATCH (k1:知识点 {courseId: $courseId})-[r:关系]->(k2:知识点 {courseId: $courseId}) RETURN k1, r, k2")
    List<KnowledgeRelation> getKnowledgeByCID(@Param("courseId") Integer courseId);

}