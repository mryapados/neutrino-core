package org.neutrinocms.core.service;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.neutrinocms.core.bo.annotation.BOService;
import org.neutrinocms.core.dao.FolderDao;
import org.neutrinocms.core.model.independant.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@BOService
@Service
@Scope(value = "singleton")
public class FolderService extends BaseService<Folder>{

	private Logger logger = Logger.getLogger(FolderService.class);

	@Autowired
	private FolderDao folderDao;

	public Folder findByName(String name) throws ServiceException {
		try {		
			return folderDao.findByName(name);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findById position", e);
		}
	}

	public Folder findByServerName(String serverName) throws ServiceException {
		try {		
			return folderDao.findByServerName(serverName);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findById position", e);
		}
	}


}
