package org.neutrinocms.core.dao;

import java.util.List;

import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.translation.Lang;
import org.neutrinocms.core.model.translation.Template;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateDao extends TranslationDao<Template> {
	
	@Query("SELECT t FROM Template t LEFT JOIN FETCH t.models m LEFT JOIN t.blocks b LEFT JOIN FETCH t.translation tr LEFT JOIN FETCH tr.translations trs WHERE (t.folders IS EMPTY OR :folder IN elements(t.folders)) AND (t.name =:name AND t.lang =:lang)")
	Template identifyWithAllExceptData(@Param("folder") Folder folder, @Param("name") String name, @Param("lang") Lang lang);
			
	@Query("SELECT t FROM Template t LEFT JOIN FETCH t.models m LEFT JOIN t.blocks b LEFT JOIN FETCH t.translation tr LEFT JOIN FETCH tr.translations trs WHERE (t.folders IS EMPTY) AND (t.name =:name AND t.lang =:lang)")
	Template identifyWithAllExceptData(@Param("name") String name, @Param("lang") Lang lang);
			
	@Query("SELECT t FROM Template t WHERE t.kind ='BLOCK' AND t.models IS EMPTY")
	List<Template> findAllBlockNotAffected();
	
	@Query("SELECT t FROM Template t WHERE t.kind ='BLOCK' OR t.kind = 'PAGEBLOCK'")
	List<Template> findAllBlockAndPageBlock();
	
	@Query("SELECT t FROM Template t LEFT JOIN FETCH t.models m")
	List<Template> findAllWithModels();
	
}
