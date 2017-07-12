package org.neutrinocms.core.dao;

import java.util.List;

import org.neutrinocms.core.model.independant.MapTemplate;
import org.neutrinocms.core.model.independant.Position;
import org.neutrinocms.core.model.translation.Translation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MapTemplateDao extends BaseDao<MapTemplate> {
	@Query("SELECT mt FROM MapTemplate mt WHERE mt.model=:model AND mt.position=:position ORDER BY mt.ordered")
	List<MapTemplate> findAllForModelAndPosition(@Param("model") Translation model, @Param("position") Position position);

}
