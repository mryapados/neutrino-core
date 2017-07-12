package org.neutrinocms.core.model.independant;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;


@Entity
@Table(name = "map_template")
public class MapTemplate implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="model")
	private Translation model;

	@NotNull
	@ManyToOne
	@JoinColumn(name="block")
	private Template block;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="position")
	private Position position;
	
	@Column(name = "ordered")
	private Integer ordered;

	@OneToMany(mappedBy = "mapTemplate")
	private List<NData> datas;
	
	@Transient
	public String getObjectType() {
		return "MapTemplate";
	}
	
	public MapTemplate() {

	}
	
	public MapTemplate(Integer id, Template model, Template block, Position position, Integer ordered) {
		super();
		this.id = id;
		this.model = model;
		this.block = block;
		this.position = position;
		this.ordered = ordered;
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Translation getModel() {
		return model;
	}

	public void setModel(Translation model) {
		this.model = model;
	}

	public Template getBlock() {
		return block;
	}

	public void setBlock(Template block) {
		this.block = block;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Integer getOrdered() {
		return ordered;
	}

	public void setOrdered(Integer ordered) {
		this.ordered = ordered;
	}

	public List<NData> getDatas() {
		return datas;
	}

	public void setDatas(List<NData> datas) {
		this.datas = datas;
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
		MapTemplate other = (MapTemplate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
