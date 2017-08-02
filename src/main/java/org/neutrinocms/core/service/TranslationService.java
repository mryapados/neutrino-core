package org.neutrinocms.core.service;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.constant.CacheConst;
import org.neutrinocms.core.dao.TranslationDao;
import org.neutrinocms.core.dao.TranslationProviderDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.translation.Lang;
import org.neutrinocms.core.model.translation.Translation;
import org.neutrinocms.core.model.translation.TranslationProvider;
import org.neutrinocms.core.util.IdProviderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Scope(value = "singleton")
public abstract class TranslationService<T extends Translation> extends BaseService<T>{

	private Logger logger = Logger.getLogger(TranslationService.class);
	
	@Autowired
	private TranslationDao<T> translationDao;

	@Autowired
	protected TranslationProviderDao translationProviderDao;


	@Autowired
	protected IdProviderUtil idProviderUtil;
	
	@Deprecated
	public T findByName(String name) throws ServiceException {
		try {
			System.out.println("findbyname " + name + " " + translationDao);
			
			return translationDao.findByName(name);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findByName Base", e);
		}
	}

	@Cacheable(CacheConst.TRANSLATION_IDENTIFY)
	public T identify(Folder folder, String name, Lang lang) throws ServiceException {
		logger.debug("Enter in identify : folder = " + folder +  "; name = " + name + "; lang = " + lang);
		try {
			if (folder == null) return translationDao.identify(name, lang);
			else return translationDao.identify(folder, name, lang);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur identify Base", e);
		}
	}
	
	@Override
	@Transactional
	public T save(T base) throws ServiceException {
		logger.debug("appel de la methode save Base " + base.getClass());
		try {
			TranslationProvider translationProvider = base.getTranslation();
			if (translationProvider != null){
				if (translationProvider.getId() == null) {
					translationProviderDao.save(translationProvider);
				}
			}
			return translationDao.save(base);
		} catch (PersistenceException e) {
			logger.error("erreur save Base " + e.getMessage());
			throw new ServiceException("erreur save Base", e);
		}
	}

	@SuppressWarnings("unchecked")
	public T translate(T base, Lang lang) throws ServiceException {
		boolean fromSomething = base.getId() != null;
		if (fromSomething) base = (T) idProviderUtil.getFullObject(base.getClass(), base.getId());
		
		base.setId(null);

		TranslationProvider translation = base.getTranslation();
		if (translation == null){
			translation = new TranslationProvider();
		}
		base.setLang(lang);
		base.setTranslation(translation);
		base.setName(base.getName());
		
		return base;
	}
	


}
