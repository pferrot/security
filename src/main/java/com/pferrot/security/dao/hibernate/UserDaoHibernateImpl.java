package com.pferrot.security.dao.hibernate;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pferrot.security.dao.UserDao;
import com.pferrot.security.model.User;

public class UserDaoHibernateImpl extends HibernateDaoSupport implements UserDao{

	public Long createUser(User user) {
		return (Long)getHibernateTemplate().save(user);
	}

	public void deleteUser(User user) {
		getHibernateTemplate().delete(user);		
	}

	public User findUser(Long id) {
		return (User)getHibernateTemplate().load(User.class, id);
	}

	public User findUser(String username) {
		List<User> list = getHibernateTemplate().find("from User user where user.username = ?", username);
		if (list == null ||
			list.isEmpty()) {
			return null;
		}
		else if (list.size() == 1) {
			return list.get(0);
		}
		else {
			throw new DataIntegrityViolationException("More that one user with username '" + username + "'");
		}
	}

	public void updateUser(User user) {
		getHibernateTemplate().update(user);		
	}
}
