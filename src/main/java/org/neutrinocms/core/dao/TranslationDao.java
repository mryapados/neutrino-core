package org.neutrinocms.core.dao;

import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.translation.Lang;
import org.neutrinocms.core.model.translation.Translation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationDao<T extends Translation> extends BaseDao<T> {	
	
//	@Query("SELECT t FROM #{#entityName} t WHERE t.name =:name OR (t.name =:name AND t.objectType = #{#entityName})")
//	T findByName(@Param("name") String name);
	
	@Query("SELECT t FROM #{#entityName} t WHERE t.name =:name")
	T findByName(@Param("name") String name);

	 
	@Query("SELECT t FROM #{#entityName} t WHERE (t.folders IS EMPTY OR :folder IN elements(t.folders)) AND (t.name =:name AND t.lang =:lang)")
	T identify(@Param("folder") Folder folder, @Param("name") String name, @Param("lang") Lang lang);
	
	@Query("SELECT t FROM #{#entityName} t WHERE (t.folders IS EMPTY) AND (t.name =:name AND t.lang =:lang)")
	T identify(@Param("name") String name, @Param("lang") Lang lang);
	
	
	
	
	
}
