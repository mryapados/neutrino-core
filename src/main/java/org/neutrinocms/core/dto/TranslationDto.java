package org.neutrinocms.core.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.model.translation.Translation;

public class TranslationDto extends IdProviderDto implements Serializable {
	private static final long serialVersionUID = 1L;	
	@NotNull
	private Date dateAdded;
	
	@NotNull
	private Date dateUpdated;
	
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.BASIC)
	private String description;

	private LangDto lang;
	
	public TranslationDto() {

	}

	public TranslationDto(String objectType, Integer id, String name, Date dateAdded, Date dateUpdated, String description, LangDto lang) {
		super(objectType, id, name);
		this.dateAdded = dateAdded;
		this.dateUpdated = dateUpdated;
		this.description = description;
		this.lang = lang;
	}

	public static TranslationDto from(Translation translation) {
		return new TranslationDto(translation.getObjectType(), translation.getId(), translation.getName(), translation.getDateAdded(), translation.getDateUpdated(), translation.getDescription(), LangDto.from(translation.getLang()));
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LangDto getLang() {
		return lang;
	}

	public void setLang(LangDto lang) {
		this.lang = lang;
	}


	
}
