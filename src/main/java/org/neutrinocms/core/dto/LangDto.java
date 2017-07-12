package org.neutrinocms.core.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.model.translation.Lang;

public class LangDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(length = 5, name = "code")
	private String code;

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(name = "name")
	private String name;
	
	public LangDto() {

	}

	public LangDto(Integer id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public static LangDto from(Lang lang) {
		if (lang == null) return null;
		return new LangDto(lang.getId(), lang.getCode(), lang.getName());
	}

	public static Lang to(LangDto langDto) {
		if (langDto == null) return null;
		return new Lang(langDto.getId(), langDto.getCode(), langDto.getName());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
