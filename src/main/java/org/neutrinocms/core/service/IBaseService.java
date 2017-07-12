package org.neutrinocms.core.service;

import java.util.List;

import org.neutrinocms.core.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

public interface IBaseService<T> {

	T save(T base) throws ServiceException;
	void remove(T base) throws ServiceException;
	void remove(Iterable<T> base) throws ServiceException;
	void removeById(Integer id) throws ServiceException;
	T findOne(Integer id) throws ServiceException;
	long count() throws ServiceException;
	
	T findOne(Specification<T> spec) throws ServiceException;
	long count(Specification<T> spec) throws ServiceException;
	
	
	Iterable<T>findAll() throws ServiceException;
	Page<T> findAll(Pageable pageable) throws ServiceException;
	List<T> findAll(Specification<T> spec) throws ServiceException;
	Page<T> findAll(Specification<T> spec, Pageable pageable) throws ServiceException;
    List<T> findAll(Specification<T> spec, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException;
    Page<T> findAll(Specification<T> spec, Pageable pageable, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException;
    List<T> findAll(Specification<T> spec, Sort sort, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException;
    T findOne(Specification<T> spec, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException;
    
    List<T> findAll(EntityGraphType entityGraphType, String entityGraphName) throws ServiceException;
    Page<T> findAll(Pageable pageable, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException;
    List<T> findAll(Sort sort, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException;
    T findOne(EntityGraphType entityGraphType, String entityGraphName) throws ServiceException;
	
}
