package com.pferrot.security.authentication.userdetails;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.pferrot.security.model.Role;
import com.pferrot.security.model.User;
import com.pferrot.security.model.UserWithRoles;

public class CustomUserDetails implements UserDetails {
	
	private UserWithRoles userWithRoles;
	
	public CustomUserDetails(UserWithRoles userWithRoles) {
		super();
		if (userWithRoles == null) {
			throw new IllegalArgumentException("'user' parameter cannot be null'");
		}
		this.userWithRoles = userWithRoles;
	}

	public GrantedAuthority[] getAuthorities() {
		if (userWithRoles.getRoles() == null) {
			return new GrantedAuthority[0];
		}
		GrantedAuthority[] result = new GrantedAuthority[userWithRoles.getRoles().size()];
		int counter = 0;
		// Create a GrantedAuthority for each role.
		for (final Role role: userWithRoles.getRoles()) {
			CustomGrantedAuthority authority = new CustomGrantedAuthority(role);
			result[counter] = authority;
			counter++;
		}
		return result;
	}	

	public String getPassword() {
		return userWithRoles.getUser().getPassword();
	}

	public String getUsername() {
		return userWithRoles.getUser().getUsername();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return userWithRoles.getUser().getEnabled().booleanValue();
	}
}
