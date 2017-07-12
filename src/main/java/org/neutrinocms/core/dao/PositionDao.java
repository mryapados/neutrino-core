package org.neutrinocms.core.dao;

import java.util.List;

import org.neutrinocms.core.model.independant.Position;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionDao extends BaseDao<Position> {
	
	@Query("SELECT p FROM Position p WHERE p.name =:name")
	Position findByName(@Param("name") String name);	
	
	@Deprecated
	@Query("SELECT p FROM Position p LEFT JOIN FETCH p.mapTemplates m WHERE (p.name =:position AND m.model =:model) ORDER BY m.ordered")
	Position findByNameForModelWithMaps(@Param("model") Translation model, @Param("position") String positionName);
	
	@Query("SELECT p FROM Position p LEFT JOIN FETCH p.mapTemplates m WHERE (p.name =:position AND m.model IN (:model)) ORDER BY m.ordered")
	Position findByNameForModelsWithMaps(@Param("model") List<Translation> models, @Param("position") String positionName);
	
	@Query("SELECT p FROM Position p LEFT JOIN FETCH p.mapTemplates m WHERE (p.id =:position AND m.model IN (:objects)) ORDER BY m.ordered")
	Position findOneForObjectsWithMaps(@Param("objects") List<Translation> objects, @Param("position") Integer positionId);
	
	@Deprecated
    @Query("SELECT COUNT(m) FROM Position p LEFT JOIN p.mapTemplates m WHERE (p.name =:position AND m.model IN (:model)) ORDER BY m.ordered")
    Integer countByNameForModelsWithMaps(@Param("model") List<Translation> models, @Param("position") String positionName);

	@Deprecated
	@Query("SELECT p FROM Position p LEFT JOIN FETCH p.mapTemplates m WHERE m.model =:model ORDER BY m.ordered")
	List<Position> findAllForModelWithMaps(@Param("model") Template model);
	
	@Deprecated
	@Query("SELECT p FROM Position p LEFT JOIN FETCH p.mapTemplates m WHERE m.model !=:model")
	List<Position> findAllEmptyWithMaps(@Param("model") Template model);
	
}
