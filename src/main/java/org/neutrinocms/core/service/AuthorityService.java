package org.neutrinocms.core.service;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.bo.annotation.CustomService;
import org.neutrinocms.core.dao.AuthorityDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.Authority;
import org.neutrinocms.core.model.independant.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CustomService
public class AuthorityService extends BaseService<Authority>{

	private Logger logger = Logger.getLogger(AuthorityService.class);

	@Autowired
	private AuthorityDao dao;

	public Position findByName(String name) throws ServiceException {
		try {
			return dao.findByName(name);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findById position", e);
		}
	}

}
