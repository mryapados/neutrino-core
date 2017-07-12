package org.neutrinocms.core.dao;

import java.util.List;

import org.neutrinocms.core.model.independant.MapTemplate;
import org.neutrinocms.core.model.independant.NData;
import org.neutrinocms.core.model.translation.Template;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface NDataDao extends BaseDao<NData> {
	@Query("SELECT nd FROM NData nd WHERE nd.template=:template ORDER BY nd.ordered, nd.id")
	List<NData> findAllForTemplate(@Param("template") Template template);
	
	@Query("SELECT nd FROM NData nd WHERE nd.mapTemplate=:mapTemplate ORDER BY nd.ordered, nd.id")
	List<NData> findAllForMapTemplate(@Param("mapTemplate") MapTemplate mapTemplate);

	
}
