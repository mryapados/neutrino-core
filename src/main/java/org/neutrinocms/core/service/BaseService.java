package org.neutrinocms.core.service;

import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.dao.BaseDao;
import org.neutrinocms.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class BaseService<T> implements IBaseService<T>{
	
	private Logger logger = Logger.getLogger(BaseService.class);

	@Autowired
	private BaseDao<T> baseDao;

	@Override
	@Transactional
	public T save(T base) throws ServiceException {
		logger.debug("appel de la methode save Base " + base.getClass());
		try {
			return baseDao.save(base);
		} catch (PersistenceException e) {
			logger.error("erreur save Base " + e.getMessage());
			throw new ServiceException("erreur save Base", e);
		}
	}

	@Override
	@Transactional
	public void remove(T base) throws ServiceException {
		logger.debug("appel de la methode remove Base ");
		try {
			baseDao.delete(base);
		} catch (EmptyResultDataAccessException e) {
			logger.error("erreur remove Base " + e.getMessage());
			throw new ServiceException("Base id doesn't exist", e);
		} catch (PersistenceException e) {
			logger.error("erreur remove Base " + e.getMessage());
			throw new ServiceException("erreur remove Base", e);
		} catch (Exception e) {
			logger.error("erreur remove Base " + e.getMessage());
			throw new ServiceException("erreur remove Base", e);
		}
	}

	@Override
	@Transactional
	public void remove(Iterable<T> base) throws ServiceException {
		logger.debug("appel de la methode remove Base ");
		try {
			baseDao.delete(base);
		} catch (EmptyResultDataAccessException e) {
			logger.error("erreur remove Base " + e.getMessage());
			throw new ServiceException("Base id doesn't exist", e);
		} catch (PersistenceException e) {
			logger.error("erreur remove Base " + e.getMessage());
			throw new ServiceException("erreur remove Base", e);
		} catch (Exception e) {
			logger.error("erreur remove Base " + e.getMessage());
			throw new ServiceException("erreur remove Base", e);
		}
	}
	
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void removeById(Integer id) throws ServiceException {
		logger.debug("appel de la methode removeById Base id " + id);
		try {
			baseDao.delete(id);
		} catch (EmptyResultDataAccessException e) {
			logger.error("erreur remove Base " + e.getMessage());
			throw new ServiceException("Base id doesn't exist", e);
		} catch (PersistenceException e) {
			logger.error("erreur remove Base " + e.getMessage());
			throw new ServiceException("erreur removeById Base", e);
		} catch (Exception e) {
			logger.error("erreur remove Base " + e.getMessage());
			throw new ServiceException("erreur removeById Base", e);
		}
	}
	
	@Override
	public long count() throws ServiceException {
		try {
			return baseDao.count();
		} catch (PersistenceException e) {
			throw new ServiceException("erreur count Base", e);
		}
	}
	
	@Override
	public long count(Specification<T> spec) throws ServiceException {
		try {
			return baseDao.count(spec);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur count Base", e);
		}
	}
	
	@Override
	public T findOne(Specification<T> spec) throws ServiceException {
		try {
			return baseDao.findOne(spec);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findOne Base", e);
		}
	}
	
	@Override
	public T findOne(Integer id) throws ServiceException {
		try {
			return baseDao.findOne(id);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findOne Base", e);
		}
	}
	
	@Override
	public Iterable<T> findAll() throws ServiceException {
		try {
			return baseDao.findAll();
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findAll Base", e);
		}
	}

	@Override
	public Page<T> findAll(Pageable pageable) throws ServiceException {
		try {
			return baseDao.findAll(pageable);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}
	
	@Override
	public List<T> findAll(Specification<T> spec) throws ServiceException {
		try {
			return baseDao.findAll(spec);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}
	
	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) throws ServiceException {
		try {
			return baseDao.findAll(spec, pageable);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}

	@Override
	public List<T> findAll(Specification<T> spec, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException {
		try {
			return baseDao.findAll(spec, entityGraphType, entityGraphName);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}

	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException {
		try {
			return baseDao.findAll(spec, pageable, entityGraphType, entityGraphName);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}

	@Override
	public List<T> findAll(Specification<T> spec, Sort sort, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException {
		try {
			return baseDao.findAll(spec, sort, entityGraphType, entityGraphName);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}

	@Override
	public T findOne(Specification<T> spec, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException {
		try {
			return baseDao.findOne(spec, entityGraphType, entityGraphName);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}
	
	
	@Override
	public List<T> findAll(EntityGraphType entityGraphType, String entityGraphName) throws ServiceException {
		try {
			return baseDao.findAll(entityGraphType, entityGraphName);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}

	@Override
	public Page<T> findAll(Pageable pageable, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException {
		try {
			return baseDao.findAll(pageable, entityGraphType, entityGraphName);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}

	@Override
	public List<T> findAll(Sort sort, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException {
		try {
			return baseDao.findAll(sort, entityGraphType, entityGraphName);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findAll", e);
		}
	}

	@Override
	public T findOne(EntityGraphType entityGraphType, String entityGraphName) throws ServiceException {
		try {
			return baseDao.findOne(entityGraphType, entityGraphName);
		} catch (PersistenceException e) {
			throw new ServiceException("Error findOne", e);
		}
	}
	
	


}
