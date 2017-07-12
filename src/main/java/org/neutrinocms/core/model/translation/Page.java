package org.neutrinocms.core.model.translation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.bo.annotation.BOField;
import org.neutrinocms.core.bo.annotation.BOField.ValueType;
import org.neutrinocms.core.bo.annotation.BOViewUrl;

@Entity
@Table(name = "page")
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "Page.allJoins", 
		attributeNodes = { 
			@NamedAttributeNode("model")
		})
})
@BOViewUrl("/{lang.code}/page/{name}.html")
public class Page extends Translation {

	private static final long serialVersionUID = 1L;
	
	@BOField(type = ValueType.VARCHAR255, inList = false)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(name = "meta_description")
	private String metaDescription;
	
	@BOField(type = ValueType.VARCHAR255, inList = false)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(name = "meta_title")
	private String metaTitle;
	
	@BOField(type = ValueType.VARCHAR255, inList = false)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(name = "meta_keywords")
	private String metaKeyWords;
	
	@BOField(type = ValueType.VARCHAR50, inList = false)
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(name = "context")
	private String context;
	
	@BOField(type = ValueType.TOBJECT)
	@OneToOne
	@JoinColumn(name="model_id")
	private Template model;
	
	@BOField(type = ValueType.TOBJECT)
	@OneToOne
	@JoinColumn(name="parent_id")
	private Page parent;
	
	@BOField(type = ValueType.DATETIME)
	@Column(name = "publish_date")
	private Date publishDate;
	
	@BOField(type = ValueType.DATETIME)
	@Column(name = "archive_date")
	private Date archiveDate;
	
	public Page() {
		super();
		this.setPublishDate(new Date());
	}
	
	public Page(Integer id, String name, Date dateAdd, Date dateUpdated, String description, Lang lang, String context, Template model) {
		super(id, name, dateAdd, dateUpdated, description, lang);
		this.context = context;
		this.model = model;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getMetaKeyWords() {
		return metaKeyWords;
	}

	public void setMetaKeyWords(String metaKeyWords) {
		this.metaKeyWords = metaKeyWords;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Template getModel() {
		return model;
	}

	public void setModel(Template model) {
		this.model = model;
	}

	public Page getParent() {
		return parent;
	}

	public void setParent(Page parent) {
		this.parent = parent;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}
	
	
}
