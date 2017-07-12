package org.neutrinocms.core.bean;

import java.io.Serializable;

import org.neutrinocms.core.model.independant.NType.ValueType;

public class NDataValue implements Serializable { 

	private static final long serialVersionUID = 1L;
	
	public ValueType type;
	public Object value;
	public String objectType;
	
	public NDataValue(ValueType type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}
	public NDataValue(ValueType type, Object value, String objectType) {
		super();
		this.type = type;
		this.value = value;
		this.objectType = objectType;
	}
	
	public ValueType getType() {
		return type;
	}
	public void setType(ValueType type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
}