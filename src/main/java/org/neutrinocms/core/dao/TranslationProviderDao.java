package org.neutrinocms.core.dao;

import org.neutrinocms.core.model.translation.TranslationProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationProviderDao extends JpaRepository<TranslationProvider, Integer> {
	
	
}
