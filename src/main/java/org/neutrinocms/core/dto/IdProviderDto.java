package org.neutrinocms.core.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.model.IdProvider;

public class IdProviderDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String objectType;
	
	private Integer id;
	
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String name;
	
		
	public IdProviderDto() {

	}

	public IdProviderDto(String objectType, Integer id, String name) {
		super();
		this.objectType = objectType;
		this.id = id;
		this.name = name;
	}

	public static IdProviderDto from(IdProvider idProvider) {
		return new IdProviderDto(idProvider.getObjectType(), idProvider.getId(), idProvider.getName());
	}

	
	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
