package org.neutrinocms.core.service;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.conf.security.api.security.JwtUserFactory;
import org.neutrinocms.core.dao.UserDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.independant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
	
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
 
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userDao.FindByLogin(login);
	    if( user == null ) throw new UsernameNotFoundException("User " + login + " not found");	    
	    return JwtUserFactory.create(user);
    }

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
    
    
    
}
