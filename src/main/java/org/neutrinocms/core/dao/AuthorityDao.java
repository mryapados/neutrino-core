package org.neutrinocms.core.dao;

import java.util.List;

import org.neutrinocms.core.model.Authority;
import org.neutrinocms.core.model.independant.Position;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityDao extends BaseDao<Authority> {
	@Query("SELECT a FROM Authority a WHERE a.name =:name")
	Position findByName(@Param("name") String name);	
}
