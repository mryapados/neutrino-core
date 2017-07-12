package org.neutrinocms.core.model.independant;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
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
@Table(name = "position")
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "Position.allJoins", 
		attributeNodes = { 
			@NamedAttributeNode("mapTemplates")
		})
})
public class Position implements IdProvider, Serializable{

	private static final long serialVersionUID = 1L;

	@BOField(type = ValueType.INTEGER, editable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@BOField(type = ValueType.VARCHAR50, defaultField = true, sortBy = SortType.ASC, sortPriority = 200)
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(name = "name")
	private String name;
	
	@BOField(type = ValueType.COLLECTION, ofType = ValueType.OBJECT)
	@OneToMany(mappedBy = "position")
	private List<MapTemplate> mapTemplates;

	@Transient
	@Override
	public String getObjectType() {
		return "Position";
	}
	
	public Position(){
		
	}
	public Position(Integer id, String name, List<MapTemplate> mapTemplates) {
		this.id = id;
		this.name = name;
		this.mapTemplates = mapTemplates;
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
	public List<MapTemplate> getMapTemplates() {
		return mapTemplates;
	}
	public void setMapTemplates(List<MapTemplate> mapTemplates) {
		this.mapTemplates = mapTemplates;
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
		Position other = (Position) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}




}
