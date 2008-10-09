package com.pferrot.security.dao.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pferrot.security.dao.UserDao;
import com.pferrot.security.model.User;

public class UserDaoHibernateImpl extends HibernateDaoSupport implements UserDao{

	public Integer createUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public User findUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User findUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}
}
