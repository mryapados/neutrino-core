package org.neutrinocms.core.service;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.bo.annotation.BOService;
import org.neutrinocms.core.dao.LangDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.translation.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@BOService
@Service
@Scope(value = "singleton")
public class LangService extends BaseService<Lang>{
	
	private Logger logger = Logger.getLogger(TranslationService.class);
	
	@Autowired
	private LangDao langDao;
	
	public Lang findByCode(String code) throws ServiceException {
		try {
			return langDao.findByCode(code);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findByName Base", e);
		}
	}
}
