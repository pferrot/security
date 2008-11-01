package com.pferrot.security.dao;

import com.pferrot.security.model.Role;

public interface RoleDao {
	
	Role findRole(String name);
	
	void updateRole(Role role);	
}
