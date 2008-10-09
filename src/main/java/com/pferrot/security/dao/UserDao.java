package com.pferrot.security.dao;

import com.pferrot.security.model.User;

public interface UserDao {
	
	Integer createUser(User user);
	
	User findUser(Long id);
	
	User findUser(String username);
	
	void updateUser(User user);
	
	void deleteUser(User user);
}
