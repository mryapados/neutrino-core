package org.neutrinocms.core.service;

import org.neutrinocms.core.model.translation.Translation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "singleton")
public class TObjectService extends TranslationService<Translation>{
	
	
}
