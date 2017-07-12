package org.neutrinocms.core.bean;

import java.io.Serializable;
import java.util.Date;

public class NFile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String rights;
	private Date date;
	private Long size;
	private String type;
	private Object tag;
	
	public NFile(String name, String rights, Date date, Long size, String type, Object tag) {
		super();
		this.name = name;
		this.rights = rights;
		this.date = date;
		this.size = size;
		this.type = type;
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Object getTag() {
		return tag;
	}
	public void setTag(Object tag) {
		this.tag = tag;
	}

	
	
}