package org.neutrinocms.core.service;

import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.conf.security.api.security.JwtUserFactory;
import org.neutrinocms.core.dao.UserDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.Authority;
import org.neutrinocms.core.model.AuthorityName;
import org.neutrinocms.core.model.independant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public User save(User base) throws ServiceException {
		logger.debug("appel de la methode save User " + base.getClass());
		try {
			return userDao.save(base);
		} catch (PersistenceException e) {
			logger.error("erreur save User " + e.getMessage());
			throw new ServiceException("erreur save User", e);
		}
	}

	public boolean userHasRole(User user, AuthorityName authorityName){
		List<Authority> authorities = user.getAuthorities();
		if (authorities == null) return false;
		for (Authority authority : authorities) {
			if (authority.getName().equals(authorityName)) {
				return true;
			}
		}
		return false;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
    
    
    
}
