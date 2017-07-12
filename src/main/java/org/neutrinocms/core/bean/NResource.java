package org.neutrinocms.core.bean;

import java.io.Serializable;

import org.neutrinocms.core.bo.annotation.BOResource.ResourceType;

public class NResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private ResourceType type;
	private String value;
	
	public NResource() {

	}

	public NResource(ResourceType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "NResource [type=" + type + ", value=" + value + "]";
	}
	
}