package com.pferrot.security.authentication.userdetails;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.pferrot.security.dao.UserDao;
import com.pferrot.security.model.User;
import com.pferrot.security.model.UserWithRoles;


// TODO: also implement UserDetailsManager and GroupManager
public class CustomUserDetailsService implements UserDetailsService {
	
	private UserDao userDao;

	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException, DataAccessException {
		
		UserWithRoles userWithRoles = userDao.findUserWithRoles(username);
		if (userWithRoles == null) {
			throw new UsernameNotFoundException("No user with username '" +  username + "' could be found");
		}
		else if (userWithRoles.getRoles() == null ||
				 userWithRoles.getRoles().size() == 0) {
			throw new UsernameNotFoundException("User with username '" +  username + "' is not associated to any role");
			
		}
		UserDetails userDetails = new CustomUserDetails(userWithRoles);
		return userDetails;
	}		

//	public void changePassword(final String oldPassword, final String newPassword) {
//		throw new RuntimeException("TODO");	
//	}
//
//	public void createUser(final UserDetails userDetails) {
//		throw new RuntimeException("TODO");		
//	}

	// TODO: can be made more performant.
//	public void deleteUser(final String username) {
//		User user = userDao.getUser(username);
//		userDao.deleteUser(user);		
//	}

//	public void updateUser(final UserDetails userDetails) {
//		throw new RuntimeException("TODO");		
//	}

	// TODO: can be made more performant.
//	public boolean userExists(final String username) {
//		User user = userDao.getUser(username);
//		return user != null;
//	}	
	
	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}	
}
