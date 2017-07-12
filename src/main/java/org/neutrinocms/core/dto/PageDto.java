package org.neutrinocms.core.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.model.translation.Page;

public class PageDto extends TranslationDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String context;
	
	private TemplateDto model;

	public PageDto() {

	}

	public PageDto(String objectType, Integer id, String name, Date dateAdded, Date dateUpdated, String description, LangDto langDto, String context, TemplateDto model) {
		super(objectType, id, name, dateAdded, dateUpdated, description, langDto);
		this.context = context;
		this.model = model;
	}

	public static PageDto from(Page page) {
		return new PageDto(page.getObjectType(), page.getId(), page.getName(), page.getDateAdded(), 
				page.getDateUpdated(), page.getDescription(), LangDto.from(page.getLang()),
				page.getContext(), TemplateDto.from(page.getModel()));
	}

	public static Page to(PageDto pageDto){
		return new Page(pageDto.getId(), pageDto.getName(), pageDto.getDateAdded(), pageDto.getDateUpdated(), pageDto.getDescription(), LangDto.to(pageDto.getLang()), pageDto.getContext(), TemplateDto.to(pageDto.getModel()));
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public TemplateDto getModel() {
		return model;
	}

	public void setModel(TemplateDto model) {
		this.model = model;
	}

	

}
