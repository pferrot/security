package com.pferrot.security.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "USERS")
public class User {
	
	@Id @GeneratedValue
	@Column(name = "ID")	
	private Long id;
	
	@Column(name = "USERNAME", nullable = false, length = 50)
	@Audited
	private String username;
	
	@Column(name = "PASSWORD", nullable = false, length = 100)
	@Audited
	private String password;
	
	@Column(name = "ENABLED", nullable = false)
	@Audited
	private Boolean enabled;
	
	@Column(name = "CREATION_DATE", nullable = false)
	private Date creationDate;
	
	@Column(name = "LAST_LOGIN_DATE")
	private Date lastLoginDate;

	@Column(name = "ACTIVATION_CODE", nullable = false, length = 30)
	private String activationCode;
	
	@Column(name = "ACTIVATION_DATE")
	private Date activationDate;
	
	@ManyToMany(targetEntity=com.pferrot.security.model.Role.class)
	@Audited
	private Set<Role> roles = new HashSet<Role>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		if (role == null) {
			throw new IllegalArgumentException("'role' parameter must not be null");
		}
		roles.add(role);
	}

	// Not ideal for performance, but fine since there are only 2 roles in the system.
	public boolean isAdmin() {
		if (roles != null) {
			for (Role role: roles) {
				if (Role.ADMIN_ROLE_NAME.equals(role.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	// Not ideal for performance, but fine since there are only 2 roles in the system.
	public boolean isUser() {
		if (roles != null) {
			for (Role role: roles) {
				if (Role.USER_ROLE_NAME.equals(role.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
