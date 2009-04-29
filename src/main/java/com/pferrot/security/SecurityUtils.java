package com.pferrot.security;

import com.pferrot.security.authentication.userdetails.CustomUserDetails;
import com.pferrot.security.model.User;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

/**
 * Some security utils methods.
 * 
 * @author Patrice
 *
 */
public class SecurityUtils {

	/**
	* Returns the domain User object for the currently logged in user, or null
	* if no User is logged in.
	* 
	* @return User object for the currently logged in user, or null if no User
	*         is logged in.
	*/
	public static User getCurrentUser() {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null) {
			return null;
		}
		
	    final Object principal = authentication.getPrincipal();

	    if (principal instanceof CustomUserDetails) {
	    	return ((CustomUserDetails) principal).getUser();
	    }

	    // principal object is either null or represents anonymous user -
	    // neither of which our domain User object can represent - so return null
	    return null;
	}
	
	/**
	 * Returns the username of the current authenticated user, or null if no user
	 * is logged in.
	 * 
	 * @return Username for the currently logged in user, or null if no user
	 *         is logged in.
	 */
	public static String getCurrentUsername() {
		final User currentUser = getCurrentUser();
		if (currentUser != null) {
			final String username = currentUser.getUsername();
			// No username? Should never happen.
			if (username == null || username.trim().length() == 0) {
				throw new SecurityException("User without a username: " + currentUser.getId());
			}
			return username;
		}
		else {
			return null;
		}
	}

	/**
	 * Utility method to determine if the current user is logged in / authenticated.
	 * <p>
	 * Equivalent of calling:
	 * <p>
	 * <code>getCurrentUser() != null</code>
	 * 
	 * @return if user is logged in
	 */
	public static boolean isLoggedIn() {
	    return getCurrentUser() != null;
	}
}
