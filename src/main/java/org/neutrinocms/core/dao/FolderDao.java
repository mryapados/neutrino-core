package org.neutrinocms.core.dao;

import org.neutrinocms.core.model.independant.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderDao extends BaseDao<Folder> {
	
	@Query("SELECT p FROM Folder p WHERE p.name =:name")
	Folder findByName(@Param("name") String name);	
	
	@Query("SELECT p FROM Folder p WHERE :serverName IN elements(p.serverName)")
	Folder findByServerName(@Param("serverName") String serverName);	
	
}
