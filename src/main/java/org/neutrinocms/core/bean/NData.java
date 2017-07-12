package org.neutrinocms.core.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.neutrinocms.core.model.IdProvider;

public class NData<T extends IdProvider> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Map<String, List<NField>>> fields;
	private T objectData;
	
	public NData() {

	}
	
	
	public NData(Map<String, Map<String, List<NField>>> fields, T objectData) {
		super();
		this.fields = fields;
		this.objectData = objectData;
	}

	public Map<String, Map<String, List<NField>>> getFields() {
		return fields;
	}

	public void setFields(Map<String, Map<String, List<NField>>> fields) {
		this.fields = fields;
	}

	public T getObjectData() {
		return objectData;
	}

	public void setObjectData(T objectData) {
		this.objectData = objectData;
	}



}