package com.pferrot.security.authentication.userdetails;

import org.springframework.security.GrantedAuthority;

import com.pferrot.security.model.Role;


public class CustomGrantedAuthority implements GrantedAuthority {
	
	private static final String ROLE_PREFIX = "ROLE_";
	private Role role;

	public CustomGrantedAuthority(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException("'role' parameter must not be null");
		}
		this.role = role;		
	}

	public String getAuthority() {
		return ROLE_PREFIX + role.getName();
	}

	public int compareTo(final Object o) {
		if (o == null) {
			return 1;
		}
		else if (!(o instanceof CustomGrantedAuthority)) {
			return 1;
		}
		CustomGrantedAuthority other = (CustomGrantedAuthority)o;
		if (getAuthority() == null) {
			if (other.getAuthority() == null) {
				return 0;
			}
			else {
				return -1;
			}
		}					
		return getAuthority().compareTo(other.getAuthority());
	}
}
