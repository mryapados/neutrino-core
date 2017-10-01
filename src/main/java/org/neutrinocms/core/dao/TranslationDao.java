package org.neutrinocms.core.dao;

import java.util.List;

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
	
	@Query("SELECT t FROM #{#entityName} t WHERE t.name =:name AND (t.active = 1)")
	T findByName(@Param("name") String name);

	 
	@Query("SELECT t FROM #{#entityName} t WHERE (t.folders IS EMPTY OR :folder IN elements(t.folders)) AND (t.name =:name AND t.lang =:lang) AND (t.active = 1)")
	T identify(@Param("folder") Folder folder, @Param("name") String name, @Param("lang") Lang lang);
	
	@Query("SELECT t FROM #{#entityName} t WHERE (t.folders IS EMPTY) AND (t.name =:name AND t.lang =:lang) AND (t.active = 1)")
	T identify(@Param("name") String name, @Param("lang") Lang lang);
	
	@Query("SELECT trs FROM #{#entityName} t JOIN t.translation tr JOIN tr.translations trs WHERE (t.id =:id) AND (trs.lang != t.lang) AND (t.active = 1 and trs.active = 1)")
	List<T> getActiveTranslations(@Param("id") Integer id);
	
	@Query("SELECT trs FROM #{#entityName} t JOIN t.translation tr JOIN tr.translations trs WHERE (t.id =:id) AND (trs.lang != t.lang)")
	List<T> getTranslations(@Param("id") Integer id);
	
}
