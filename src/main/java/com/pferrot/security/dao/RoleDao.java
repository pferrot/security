package com.pferrot.security.dao;

import com.pferrot.security.model.Role;

public interface RoleDao {
	
	Long createRole(Role role);
	
	Role findRole(String name);
	Role findRole(Long id);
	
	void updateRole(Role role);	
}
