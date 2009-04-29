package com.pferrot.security.authentication.userdetails;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.pferrot.security.model.Role;
import com.pferrot.security.model.User;

public class CustomUserDetails implements UserDetails {
	
	private User user;
	
	public CustomUserDetails(User user) {
		super();
		if (user == null) {
			throw new IllegalArgumentException("'user' parameter cannot be null'");
		}
		this.user = user;
	}

	public GrantedAuthority[] getAuthorities() {
		if (user.getRoles() == null) {
			return new GrantedAuthority[0];
		}
		GrantedAuthority[] result = new GrantedAuthority[user.getRoles().size()];
		int counter = 0;
		// Create a GrantedAuthority for each role.
		for (final Role role: user.getRoles()) {
			CustomGrantedAuthority authority = new CustomGrantedAuthority(role);
			result[counter] = authority;
			counter++;
		}
		return result;
	}	
	
	public User getUser() {
		return user;
	}
	
	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		return user.getUsername();
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
		return user.getEnabled().booleanValue();
	}
}
