package org.neutrinocms.core.model.translation;

import org.neutrinocms.core.model.IdProvider;

public interface ITranslation extends IdProvider {

	Lang getLang();
	void setLang(Lang lang);

	TranslationProvider getTranslation();
	void setTranslation(TranslationProvider translation);
	
}
