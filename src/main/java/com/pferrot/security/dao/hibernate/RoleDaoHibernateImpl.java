package com.pferrot.security.dao.hibernate;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pferrot.security.dao.RoleDao;
import com.pferrot.security.model.Role;

public class RoleDaoHibernateImpl extends HibernateDaoSupport implements RoleDao {

	public Long createRole(Role role) {
		return (Long)getHibernateTemplate().save(role);
	}	

	public Role findRole(String name) {
		List<Role> list = getHibernateTemplate().find("from Role role where role.name = ?", name);
		if (list == null ||
			list.isEmpty()) {
			return null;
		}
		else if (list.size() == 1) {
			return list.get(0);
		}
		else {
			throw new DataIntegrityViolationException("More that one role with name '" + name + "'");
		}
	}

	public void updateRole(Role role) {
		getHibernateTemplate().update(role);			
	}

}
