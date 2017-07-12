package org.neutrinocms.core.repository;

import java.io.Serializable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

//http://stackoverflow.com/questions/26291143/spring-data-jpa-jpaspecificationexecutor-entitygraph
@NoRepositoryBean
public interface NeutrinoCoreRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    List<T> findAll(Specification<T> spec, EntityGraphType entityGraphType, String entityGraphName);
    Page<T> findAll(Specification<T> spec, Pageable pageable, EntityGraphType entityGraphType, String entityGraphName);
    List<T> findAll(Specification<T> spec, Sort sort, EntityGraphType entityGraphType, String entityGraphName);
    T findOne(Specification<T> spec, EntityGraphType entityGraphType, String entityGraphName);

    List<T> findAll(EntityGraphType entityGraphType, String entityGraphName);
    Page<T> findAll(Pageable pageable, EntityGraphType entityGraphType, String entityGraphName);
    List<T> findAll(Sort sort, EntityGraphType entityGraphType, String entityGraphName);
    T findOne(EntityGraphType entityGraphType, String entityGraphName);

}