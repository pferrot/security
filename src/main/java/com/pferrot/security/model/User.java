package com.pferrot.security.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private Integer id;
//	private boolean idModified;
	private String username;
	private boolean usernameModified;
	private String password;
	private boolean passwordModified;
	private Boolean enabled;
	private boolean enabledModified;
	private Date creationDate;
//	private boolean creationDateModified;
	private Date lastLoginDate;
	private boolean lastLoginDateModified;
	private Date lastModifiedDate;
	
	
	public User() {
		super();
	}

	public User(Integer id, 
				String username, 
				String password, 
				Boolean enabled, 
				Date lastLoginDate, 
				Date creationDate,
				Date lastModifiedDate) {
		super();
		this.creationDate = creationDate;
		this.enabled = enabled;
		this.id = id;
		this.lastLoginDate = lastLoginDate;
		this.lastModifiedDate = lastModifiedDate;
		this.password = password;
		this.username = username;
	}

	public Integer getId() {
		return id;
	}
	
//	public void setId(Integer id) {
//		setIdModified(true);
//		this.id = id;
//	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		setUsernameModified(true);
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		setPasswordModified(true);
		this.password = password;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		setEnabledModified(true);
		this.enabled = enabled;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
//	public void setCreationDate(Date creationDate) {
//		setCreationDateModified(true);
//		this.creationDate = creationDate;
//	}
	
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	
	public void setLastLoginDate(Date lastLoginDate) {
		setLastLoginDateModified(true);
		this.lastLoginDate = lastLoginDate;
	}
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	////////////////////////////////////////////////////////////
	// To check what fields what need to be updated in the DB.
//	public boolean isIdModified() {
//		return idModified;
//	}
//	
//	protected void setIdModified(boolean idModified) {
//		this.idModified = idModified;
//	}
	
	public boolean isUsernameModified() {
		return usernameModified;
	}
	
	protected void setUsernameModified(boolean usernameModified) {
		this.usernameModified = usernameModified;
	}
	
	public boolean isPasswordModified() {
		return passwordModified;
	}
	
	protected void setPasswordModified(boolean passwordModified) {
		this.passwordModified = passwordModified;
	}
	
	public boolean isEnabledModified() {
		return enabledModified;
	}
	
	protected void setEnabledModified(boolean enabledModified) {
		this.enabledModified = enabledModified;
	}
	
//	public boolean isCreationDateModified() {
//		return creationDateModified;
//	}
//	
//	protected void setCreationDateModified(boolean creationDateModified) {
//		this.creationDateModified = creationDateModified;
//	}
	
	public boolean isLastLoginDateModified() {
		return lastLoginDateModified;
	}
	
	protected void setLastLoginDateModified(boolean lastLoginDateModified) {
		this.lastLoginDateModified = lastLoginDateModified;
	}	
}
