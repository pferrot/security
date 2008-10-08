package com.pferrot.security.authentication.listener;

import java.util.Date;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.event.authentication.AbstractAuthenticationEvent;
import org.springframework.security.event.authentication.AuthenticationSuccessEvent;

import com.pferrot.security.dao.UserDao;
import com.pferrot.security.model.User;


public class LastLoginListener implements ApplicationListener {

	UserDao userDao;

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AbstractAuthenticationEvent) {
			
			// Login failed.
//			if (event instanceof AbstractAuthenticationFailureEvent) {
//			}

			// Login successful.
			if (event instanceof AuthenticationSuccessEvent){
				AuthenticationSuccessEvent authenticationSuccessEvent = (AuthenticationSuccessEvent) event;
				String username = authenticationSuccessEvent.getAuthentication().getName();
				User user = userDao.findUser(username);
				user.setLastLoginDate(new Date());
				userDao.updateUser(user);
			}
		}
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}
}
