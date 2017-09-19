package org.neutrinocms.core.model.independant;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.bo.annotation.BOField;
import org.neutrinocms.core.bo.annotation.BOField.SortType;
import org.neutrinocms.core.bo.annotation.BOField.ValueType;
import org.neutrinocms.core.model.Authority;
import org.neutrinocms.core.model.AuthorityName;
import org.neutrinocms.core.model.IdProvider;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "status")
@DiscriminatorValue(value = "user")
public class User implements IdProvider, Serializable {

	private static final long serialVersionUID = 1L;
	
	@BOField(type = ValueType.INTEGER, editable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@BOField(type = ValueType.VARCHAR50, defaultField = true, sortBy = SortType.ASC, sortPriority = 200)
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(name = "username")
	private String login;

	@BOField(type = ValueType.PASSWORD)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(name = "encrypted_password")
	private String encryptedPassword;

	@NotNull
	@BOField(type = ValueType.BOOLEAN)
	@Column(name = "enabled")
	private Boolean enabled;

	@NotNull
	@BOField(type = ValueType.DATETIME)
	@Column(name = "last_password_reset_date")
	private Date lastPasswordResetDate;
	
	@BOField(type = ValueType.COLLECTION, ofType = ValueType.OBJECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private List<Authority> authorities;

//	@Transient
//	private boolean preview;
	
	@Transient
	private String name;
	
	@Transient
	public String getObjectType() {
		return this.getClass().getSimpleName();
	}
	
	@Transient
	public boolean isAdmin() {
		if (authorities == null) return false;
		boolean isAdmin = false;
		for (Authority authority : authorities) {
			if (authority.getName().equals(AuthorityName.ROLE_ADMIN)) {
				isAdmin = true;
			}
		}
		return isAdmin;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		setLogin(name);
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptPassword(String encryptPassword) {
		this.encryptedPassword = encryptPassword;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

    public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

//	public PreviewType getPreview() {
//		if (preview){
//			if (role.equals(ROLE_ADMIN)) return PreviewType.ADMIN;
//			else return PreviewType.USER;
//		} else return PreviewType.NONE;
//	}
//
//	public void setPreview(Boolean preview) {
//		this.preview = preview;
//	}

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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}




	


}
