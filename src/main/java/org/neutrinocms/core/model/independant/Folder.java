package org.neutrinocms.core.model.independant;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.bo.annotation.BOField;
import org.neutrinocms.core.bo.annotation.BOField.SortType;
import org.neutrinocms.core.bo.annotation.BOField.ValueType;
import org.neutrinocms.core.model.IdProvider;

@Entity
@Table(name = "folder")
public class Folder implements IdProvider, Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@BOField(type = ValueType.BOOLEAN)
	@Column(name = "active")
	private boolean active;
	
	@BOField(type = ValueType.VARCHAR50, defaultField = true, sortBy = SortType.ASC, sortPriority = 200)
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(name = "name")
	private String name;
	
	@BOField(type = ValueType.COLLECTION, ofType = ValueType.VARCHAR255)
	@NotNull
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="servername", joinColumns=@JoinColumn(name="servername_id"))
	@Column(name = "servername")
	private List<String> serverName;

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(name = "path")
	private String path;
	
	@Transient
	private boolean serverNameForced;
	
	@Transient
	@Override
	public String getObjectType() {
		return "Folder";
	}
	
	public Folder(){
		
	}
	public Folder(Integer id, String name, List<String> serverName, String path) {
		super();
		this.id = id;
		this.name = name;
		this.serverName = serverName;
		this.path = path;
	}
	@Override
	public Integer getId() {
		return id;
	}
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public boolean isActive() {
		return active;
	}
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getServerName() {
		return serverName;
	}

	public void setServerName(List<String> serverName) {
		this.serverName = serverName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public boolean isServerNameForced() {
		return serverNameForced;
	}

	public void setServerNameForced(boolean serverNameForced) {
		this.serverNameForced = serverNameForced;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Folder other = (Folder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Folder [id=" + id + ", name=" + name + ", serverName=" + serverName + ", path=" + path
				+ ", serverNameForced=" + serverNameForced + "]";
	}

	
	
}
