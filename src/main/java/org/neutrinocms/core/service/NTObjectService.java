package org.neutrinocms.core.service;

import org.neutrinocms.core.model.notranslation.NoTranslation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "singleton")
public class NTObjectService extends NoTranslationService<NoTranslation>{
	
}
