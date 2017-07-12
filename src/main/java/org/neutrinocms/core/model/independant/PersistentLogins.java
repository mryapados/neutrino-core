package org.neutrinocms.core.model.independant;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogins implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(name = "username", length = 255)
	private String login;
	
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(name = "series", length = 255)
	private String series;
	
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(name = "token", length = 255)
	private String token;
	
	@NotNull
	@Column(name = "last_used")
	private Date lastUsed;
	
	
}
