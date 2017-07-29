package org.neutrinocms.core.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.conf.security.api.security.JwtUserFactory;
import org.neutrinocms.core.dao.UserDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.Authority;
import org.neutrinocms.core.model.independant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
	
	private Logger logger = Logger.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
    
//    @Autowired
//    public UserService(UserDao userDao)
//    {
//        this.userDao = userDao;
//    }
    
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
	    //UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getEncryptedPassword(), mapToGrantedAuthorities(user.getAuthorities()));
	    //return userDetails;
	    
	    return JwtUserFactory.create(user);
    }

//    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
//        return authorities.stream()
//            .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
//            .collect(Collectors.toList());
//    }

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
    
    
    
}
