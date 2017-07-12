package org.neutrinocms.core.dao;

import org.neutrinocms.core.repository.NeutrinoCoreRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BaseDao<T> extends NeutrinoCoreRepository<T, Integer>, JpaSpecificationExecutor<T> {
	
	
}
