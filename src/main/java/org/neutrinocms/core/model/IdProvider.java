package org.neutrinocms.core.model;

public interface IdProvider {
	
	String getObjectType();
	
	Integer getId();
	void setId(Integer id);
	
	String getName();
	void setName(String name);
	
	boolean isActive();
	void setActive(boolean isActive);
}
