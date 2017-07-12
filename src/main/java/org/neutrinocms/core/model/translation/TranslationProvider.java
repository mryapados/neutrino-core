package org.neutrinocms.core.model.translation;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "translation_provider")
public class TranslationProvider implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Access(AccessType.PROPERTY)
	private Integer id;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="translation")
	@MapKey(name = "lang")
	private Map<Lang, Translation> translations;

	@Transient
	public String getObjectType() {
		return this.getClass().getSimpleName();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<Lang, Translation> getTranslations() {
		return translations;
	}

	public void setTranslations(Map<Lang, Translation> translations) {
		this.translations = translations;
	}

}
