package org.neutrinocms.core.dao;

import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.notranslation.NoTranslation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoTranslationDao<T extends NoTranslation> extends BaseDao<T> {
	@Query("SELECT t FROM #{#entityName} t WHERE t.name =:name AND (t.active = 1)")
	T findByName(@Param("name") String name);
	
	@Query("SELECT t FROM #{#entityName} t WHERE (t.folders IS EMPTY OR :folder IN elements(t.folders)) AND (t.name =:name) AND (t.active = 1)")
	T identify(@Param("folder") Folder folder, @Param("name") String name);
	
	@Query("SELECT t FROM #{#entityName} t WHERE (t.folders IS EMPTY) AND (t.name =:name) AND (t.active = 1)")
	T identify(@Param("name") String name);
}
