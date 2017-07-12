package org.neutrinocms.core.service;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.dao.NoTranslationDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.notranslation.NoTranslation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "singleton")
public abstract class NoTranslationService<T extends NoTranslation> extends BaseService<T>{

	private Logger logger = Logger.getLogger(NoTranslationService.class);

	@Autowired
	private NoTranslationDao<T> noTranslationDao;

	public T findByName(String name) throws ServiceException {
		try {
			System.out.println("findbyname " + name + " " + noTranslationDao);
			
			return noTranslationDao.findByName(name);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findByName Base", e);
		}
	}
	
	@Cacheable(value="identify")
	public T identify(Folder folder, String name) throws ServiceException {
		try {
			return noTranslationDao.identify(folder, name);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur identify Base", e);
		}
	}

}
