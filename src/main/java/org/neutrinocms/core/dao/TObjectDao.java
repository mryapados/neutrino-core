package org.neutrinocms.core.dao;
//package fr.cedricsevestre.dao.engine;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.NoRepositoryBean;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import fr.cedricsevestre.entity.engine.NoTranslation;
//import fr.cedricsevestre.entity.engine.Translation;
//
//@Repository
//public interface TObjectDao extends BaseDao<Translation> {
//
//	@Query("SELECT t FROM Translation t WHERE t.objectType = :type")
//	List<Translation> findAllForType(@Param("type") String type);
//	
//}
