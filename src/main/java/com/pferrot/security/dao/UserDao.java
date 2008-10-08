package com.pferrot.security.dao;

import com.pferrot.security.model.User;
import com.pferrot.security.model.UserWithRoles;

public interface UserDao {
	
	Integer createUserWithRoles(UserWithRoles userWithRoles);
	
	User findUser(Integer id);
	UserWithRoles findUserWithRoles(Integer id);
	
	User findUser(String username);
	UserWithRoles findUserWithRoles(String username);
	
	void updateUser(User user);
	void updateUserWithRoles(UserWithRoles userWithRoles);
	
	void deleteUser(User user);
}
