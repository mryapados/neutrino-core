package org.neutrinocms.core.service;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.dao.UserDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.independant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User>{

	private Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	public User findByLogin(String login) throws ServiceException {
		try {
			return userDao.FindByLogin(login);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findByLogin player", e);
		}
	}

}
