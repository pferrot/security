package com.pferrot.security.model;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.util.StringUtils;

public class UserWithRoles implements Serializable {
	
	private User user;
	private Collection<Role> roles;
	private boolean rolesModified;
	
	public UserWithRoles(User user, Collection<Role> roles) {
		super();
		this.roles = roles;
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
//	public void setUser(User user) {
//		this.user = user;
//	}

	public Collection<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Collection<Role> roles) {
		setRolesModified(true);
		this.roles = roles;
	}
	
	public boolean add(Role e) {
		boolean added = roles.add(e);
		setRolesModified(added);		
		return added;
	}
	
	public boolean addAll(Collection<Role> c) {
		boolean added = roles.addAll(c);
		setRolesModified(added);
		return added;
	}
	
	public boolean remove(Object o) {
		boolean removed = roles.remove(o);
		setRolesModified(removed);
		return removed;
	}
	
	public boolean removeAll(Collection<Role> c) {
		boolean removed = roles.removeAll(c);
		setRolesModified(removed);
		return removed;
	}
	
	public boolean isInRole(Integer roleId) {
		if (roleId == null) {
			throw new IllegalArgumentException("'roleId' parameter must not be null");
		}
		if (roles == null) {
			return false;
		}
		for (Role role: roles) {
			if (roleId.equals(role.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInRole(String roleName) {
		if (!StringUtils.hasText(roleName)) {
			throw new IllegalArgumentException("'roleName' parameter must contain text");
		}
		if (roles == null) {
			return false;
		}
		for (Role role: roles) {
			if (roleName.equals(role.getName())) {
				return true;
			}
		}
		return false;
	}
	
	////////////////////////////////////////////////////////////
	// To check what fields what need to be updated in the DB.
	public boolean isRolesModified() {
		return rolesModified;
	}
	
	protected void setRolesModified(boolean rolesModified) {
		this.rolesModified = rolesModified;
	}		
}
