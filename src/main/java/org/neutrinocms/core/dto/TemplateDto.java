package org.neutrinocms.core.dto;

import java.util.Date;
import java.util.HashSet;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.model.independant.MapTemplate;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Template.TemplateKind;

public class TemplateDto extends TranslationDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	private TemplateKind kind;
	
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String path;

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String controller;
	
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String metaDescription;
	
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String metaTitle;
	
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String metaKeyWords;

	public TemplateDto() {

	}

	public TemplateDto(String objectType, Integer id, String name, Date dateAdded, Date dateUpdated, String description, LangDto langDto, TemplateKind kind, String path,
			String controller, String metaDescription, String metaTitle, String metaKeyWords) {
		super(objectType, id, name, dateAdded, dateUpdated, description, langDto);
		this.kind = kind;
		this.path = path;
		this.controller = controller;
		this.metaDescription = metaDescription;
		this.metaTitle = metaTitle;
		this.metaKeyWords = metaKeyWords;
	}

	public static TemplateDto from(Template template) {
		return new TemplateDto(template.getObjectType(), template.getId(), template.getName(), template.getDateAdded(), template.getDateUpdated(), template.getDescription(), LangDto.from(template.getLang()), 
				template.getKind(), template.getPath(), template.getController(), template.getMetaDescription(), template.getMetaTitle(),
				template.getMetaKeyWords());
	}

	public static Template to(TemplateDto templateDto){
		return new Template(templateDto.getId(), templateDto.getName(), templateDto.getDateAdded(), templateDto.getDateUpdated(), templateDto.getDescription(), LangDto.to(templateDto.getLang()), templateDto.getKind(), templateDto.getPath(), templateDto.getController(), new HashSet<MapTemplate>(), new HashSet<MapTemplate>(), templateDto.getMetaDescription(), templateDto.getMetaTitle(), templateDto.getMetaKeyWords());
	}

	public TemplateKind getKind() {
		return kind;
	}

	public void setKind(TemplateKind kind) {
		this.kind = kind;
	}

	public String getPath() {
		return path;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getMetaKeyWords() {
		return metaKeyWords;
	}

	public void setMetaKeyWords(String metaKeyWords) {
		this.metaKeyWords = metaKeyWords;
	}

}
