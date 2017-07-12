package org.neutrinocms.core.model.independant;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.model.independant.NType.ValueType;
import org.neutrinocms.core.model.notranslation.NoTranslation;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;

@Entity
@Table(name = "ndata")
public class NData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@Column(name = "vartype")
	private ValueType varType;
	
	@Column(length = 30, name = "propertyname")
	private String propertyName;
	
	@Column(name = "ordered")
	private Integer ordered;
	
	@Column(name = "vinteger")
	private Integer vInteger;
	
	@Column(name = "vfloat")
	private Float vFloat;
	
	@Column(name = "vdouble")
	private Double vDouble;
	
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(length = 50, name = "vvarchar50")
	private String vVarchar50;
	
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(length = 255, name = "vvarchar255")
	private String vVarchar255;	
	
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(columnDefinition="TEXT", name = "vtext")
	private String vText;
	
	@Column(name = "vdatetime")
	private Date vDateTime;
	
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(name = "vfile")
	private String vPathFile;
	
	@SafeHtml(whitelistType = WhiteListType.RELAXED)
	@Column(name = "html")
	private String vHtml;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="vtobject")
	private Translation vTObject;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="vntobject")
	private NoTranslation vNTObject;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="vobject")
	private NData vObject;

	@Column(name = "vcollection")
	private Boolean vCollection;

	@ManyToOne
	@JoinColumn(name="id_ndata")
	private NData data;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "data")
	private List<NData> datas;
	
	
	@ManyToOne
	@JoinColumn(name="id_template")
	private Template template;
	
	@ManyToOne
	@JoinColumn(name="id_maptemplate")
	private MapTemplate mapTemplate;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ValueType getVarType() {
		return varType;
	}

	public void setVarType(ValueType varType) {
		this.varType = varType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Integer getOrdered() {
		return ordered;
	}

	public void setOrdered(Integer ordered) {
		this.ordered = ordered;
	}

	public Integer getvInteger() {
		return vInteger;
	}

	public void setvInteger(Integer vInteger) {
		this.vInteger = vInteger;
	}

	public Float getvFloat() {
		return vFloat;
	}

	public void setvFloat(Float vFloat) {
		this.vFloat = vFloat;
	}

	public Double getvDouble() {
		return vDouble;
	}

	public void setvDouble(Double vDouble) {
		this.vDouble = vDouble;
	}

	public String getvVarchar50() {
		return vVarchar50;
	}

	public void setvVarchar50(String vVarchar50) {
		this.vVarchar50 = vVarchar50;
	}

	public String getvVarchar255() {
		return vVarchar255;
	}

	public void setvVarchar255(String vVarchar255) {
		this.vVarchar255 = vVarchar255;
	}

	public String getvText() {
		return vText;
	}

	public void setvText(String vText) {
		this.vText = vText;
	}

	public Date getvDateTime() {
		return vDateTime;
	}

	public void setvDateTime(Date vDateTime) {
		this.vDateTime = vDateTime;
	}

	public String getvPathFile() {
		return vPathFile;
	}

	public void setvPathFile(String vPathFile) {
		this.vPathFile = vPathFile;
	}

	public String getvHtml() {
		return vHtml;
	}

	public void setvHtml(String vHtml) {
		this.vHtml = vHtml;
	}
	
	public Translation getvTObject() {
		return vTObject;
	}

	public void setvTObject(Translation vTObject) {
		this.vTObject = vTObject;
	}

	public NoTranslation getvNTObject() {
		return vNTObject;
	}

	public void setvNTObject(NoTranslation vNTObject) {
		this.vNTObject = vNTObject;
	}

	public NData getvObject() {
		return vObject;
	}

	public void setvObject(NData vObject) {
		this.vObject = vObject;
	}

	public Boolean getvCollection() {
		return vCollection;
	}

	public void setvCollection(Boolean vCollection) {
		this.vCollection = vCollection;
	}

	public NData getData() {
		return data;
	}

	public void setData(NData data) {
		this.data = data;
	}

	public List<NData> getDatas() {
		return datas;
	}

	public void setDatas(List<NData> datas) {
		this.datas = datas;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public MapTemplate getMapTemplate() {
		return mapTemplate;
	}

	public void setMapTemplate(MapTemplate mapTemplate) {
		this.mapTemplate = mapTemplate;
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
		NData other = (NData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
