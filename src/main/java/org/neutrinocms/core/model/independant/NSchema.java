package org.neutrinocms.core.model.independant;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "nschema")
public class NSchema implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum ScopeType{
		ALL, ONE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@JoinTable(name = "nschema_columns", joinColumns = @JoinColumn(name = "nschema_id", nullable = false))
	@MapKeyColumn(name = "name", length = 30, nullable = false)
	private Map<String, NType> columns;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private ScopeType scope;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<String, NType> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, NType> columns) {
		this.columns = columns;
	}

	public ScopeType getScope() {
		return scope;
	}

	public void setScope(ScopeType scope) {
		this.scope = scope;
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
		NSchema other = (NSchema) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
	
	
	
}
