package org.neutrinocms.core.service;

import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.bo.annotation.CustomService;
import org.neutrinocms.core.constant.CacheConst;
import org.neutrinocms.core.dao.PositionDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.independant.Position;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "singleton")
@CustomService
public class PositionService extends BaseService<Position>{

	private Logger logger = Logger.getLogger(PositionService.class);

	@Autowired
	private PositionDao positionDao;

	public Position findByName(String name) throws ServiceException {
		try {
			logger.debug("Look for position : " + name);
			return positionDao.findByName(name);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findById position", e);
		}
	}

	@Deprecated
	public Position findByNameForModelWithMaps(Translation model, String positionName) throws ServiceException {
		try {
			return positionDao.findByNameForModelWithMaps(model, positionName);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findById position", e);
		}
	}
	
	@Cacheable(CacheConst.POSITION_MAPTEMPLATE)
	public Position findByNameForModelsWithMaps(List<Translation> models, String positionName) throws ServiceException {
		try {
			return positionDao.findByNameForModelsWithMaps(models, positionName);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findById position", e);
		}
	}
	
	public Position findOneForObjectsWithMaps(List<Translation> objects, Integer positionId) throws ServiceException {
		try {
			return positionDao.findOneForObjectsWithMaps(objects, positionId);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findById position", e);
		}
	}
	
	public Integer countByNameForModelsWithMaps(List<Translation> models, String positionName) throws ServiceException {
		try {
			return positionDao.countByNameForModelsWithMaps(models, positionName);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findById position", e);
		}
	}
	
	public List<Position> findAllForModelWithMaps(Template modelName) throws ServiceException {
		try {
			return positionDao.findAllForModelWithMaps(modelName);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findAllForModelWithMaps Position", e);
		}
	}
	
	public List<Position> findAllEmptyWithMaps(Template modelName) throws ServiceException {
		try {
			return positionDao.findAllEmptyWithMaps(modelName);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findAllEmptyWithMaps Position", e);
		}
	}

}
