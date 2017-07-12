package org.neutrinocms.core.dao;

import org.neutrinocms.core.model.translation.Lang;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LangDao extends BaseDao<Lang> {
	@Query("SELECT e FROM #{#entityName} e WHERE e.code =:code")
	Lang findByCode(@Param("code") String code);
}
