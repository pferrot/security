package com.pferrot.security.dao.jdbc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

import com.pferrot.core.jdbc.utils.JdbcDaoUtils;
import com.pferrot.security.dao.UserDao;
import com.pferrot.security.model.Role;
import com.pferrot.security.model.User;
import com.pferrot.security.model.UserWithRoles;

public class UserDaoJdbcImpl extends JdbcDaoSupport implements UserDao {
	
	private JdbcDaoUtils jdbcDaoUtils;

	public Integer createUserWithRoles(final UserWithRoles userWithRoles) {
		if (userWithRoles == null) {
			throw new IllegalArgumentException("'userWithRoles' parameter must not be null");
		}
		PreparedStatementCreatorFactory pscFactory = 
			new PreparedStatementCreatorFactory("insert into users (id, username, password, enabled, creation_date, last_login_date, last_modified_date) values (?, ?, ?, ?, ?, ?, ?)", 
					new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP, Types.TIMESTAMP});
		Integer id = jdbcDaoUtils.getNextId("users");
		Date now = new Date();		
		PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(new Object[]{
				id, 
				userWithRoles.getUser().getUsername(), 
				userWithRoles.getUser().getPassword(),
				userWithRoles.getUser().getEnabled(),
				now,
				userWithRoles.getUser().getLastLoginDate(),
				now });
		getJdbcTemplate().update(psc);
		
		addUserRoles(userWithRoles);
		
		return id;
	}

	public void deleteUser(final User user) {
		if (user == null) {
			throw new IllegalArgumentException("'user' parameter must not be null");
		}
		deleteUserRoles(user);
		
		PreparedStatementCreatorFactory deleteUserFactory = new PreparedStatementCreatorFactory("delete from users where id = ?", new int[]{Types.INTEGER});
		PreparedStatementCreator deleteUser = deleteUserFactory.newPreparedStatementCreator(new Object[]{new Integer(user.getId())});
		getJdbcTemplate().update(deleteUser);
	}
	
	private void deleteUserRoles(final User user) {		
		if (user == null) {
			throw new IllegalArgumentException("'user' parameter must not be null");
		}
		PreparedStatementCreatorFactory deleteUserRolesFactory = new PreparedStatementCreatorFactory("delete from users_roles where user_id = ?", new int[]{Types.INTEGER});
		PreparedStatementCreator deleteUserRoles = deleteUserRolesFactory.newPreparedStatementCreator(new Object[]{new Integer(user.getId())});
		getJdbcTemplate().update(deleteUserRoles);
	}
	
	private void addUserRoles(final Integer userID, final Collection<Role> roles) {
		if (roles != null && roles.size() > 0) {
			SqlUpdate insertRoles = new SqlUpdate();
			insertRoles.setDataSource(getDataSource());
			insertRoles.setSql("insert into users_roles (user_id, role_id) values (?, ?)");
			insertRoles.declareParameter(new SqlParameter("user_id", Types.INTEGER));
			insertRoles.declareParameter(new SqlParameter("role_id", Types.INTEGER));
			insertRoles.compile();
			for (Role role: roles) {
				Object[] parameters = new Object[] {userID, role.getId()};
				insertRoles.update(parameters);
			}
		}		
	}
	
	private void addUserRoles(final UserWithRoles userWithRoles) {
		if (userWithRoles == null) {
			throw new IllegalArgumentException("'userWithRoles' parameter must not be null");
		}
		addUserRoles(userWithRoles.getUser().getId(), userWithRoles.getRoles());
	}
	
	private Collection<Role> getRolesForUser(final User user) {
		if (user == null) {
			throw new IllegalArgumentException("'user' parameter must not be null");
		}
		// Find roles for that user.
		RolesByUserIdQuery rolesQuery = new RolesByUserIdQuery(getDataSource());
		return rolesQuery.execute(user.getId());
	}
	
	public UserWithRoles findUserWithRoles(final Integer id) {
		User user = findUser(id);
		if (user == null) {
			return null;
		}
		else {
			Collection<Role> roles = getRolesForUser(user);
			return new UserWithRoles(user, roles);
		}
	}
	
	public User findUser(final Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("'id' parameter must not be null");
		}
		UserByIdQuery userQuery = new UserByIdQuery(getDataSource());
		return getUserSingle(userQuery.execute(id));
	}
	
	public UserWithRoles findUserWithRoles(final String username) {
		User user = findUser(username);
		if (user == null) {
			return null;
		}
		else {
			Collection<Role> roles = getRolesForUser(user);
			return new UserWithRoles(user, roles);
		}
	}
	
	public User findUser(final String username) {
		if (username == null) {
			throw new IllegalArgumentException("'username' parameter must not be null");
		}
		UserByUsernameQuery userQuery = new UserByUsernameQuery(getDataSource());
		return getUserSingle(userQuery.execute(username));
	}
	
	private User getUserSingle(final List<User> users) {
		if (users == null ||
			users.size() == 0) {
			return null;
		}
		else if (users.size() == 1) {
			return users.get(0);
		}
		else {
			throw new DataIntegrityViolationException("More than one user found");
		}
	}	
	
	private void updateUser(final User user, final boolean rolesUpdated) {
		if (user == null) {
			throw new IllegalArgumentException("'user' parameter must not be null");
		}
		else if (user.getId() == null) {
			throw new IllegalArgumentException("'user' must have an ID");
		}
		Date lastModifiedDateFromDB = getUserLastModifiedDate(user.getId());
		if (lastModifiedDateFromDB.getTime() != user.getLastModifiedDate().getTime()) {
			throw new OptimisticLockingFailureException("Object has been modified by another user. Time DB: " + lastModifiedDateFromDB.getTime() + ", time object: " + user.getLastModifiedDate().getTime());
		}
		
		StringBuffer query = new StringBuffer();
		StringBuffer parameters = new StringBuffer();
		int counter = 0;
		int types[] = new int[4];
		List params = new ArrayList();
		
		query.append("update users set ");
		if (user.isUsernameModified()) {
			query.append("username = ?, ");
			types[counter] = Types.VARCHAR;
			params.add(user.getUsername());
			counter++;
		}
		if (user.isPasswordModified()) {
			query.append("password = ?, ");
			types[counter] = Types.VARCHAR;
			params.add(user.getPassword());
			counter++;
		}
		if (user.isEnabledModified()) {
			query.append("enabled = ?, ");
			types[counter] = Types.TIMESTAMP;
			params.add(user.getEnabled());
			counter++;
		}
		if (user.isLastLoginDateModified()) {
			query.append("last_login_date = ?, ");
			types[counter] = Types.TIMESTAMP;
			params.add(user.getLastLoginDate());
			counter++;
		}
		
		if (counter > 0) {
			int[] typesFinal = new int[counter+2];
			System.arraycopy(types, 0, typesFinal, 0, counter);
			
			// Do not forget to update the "last modified date".
			query.append("last_modified_date = ? ");
			typesFinal[counter] = Types.TIMESTAMP;
			params.add(new Date());
			
			query.append("where id = ?");
			typesFinal[counter+1] = Types.INTEGER;
			params.add(user.getId());
			
			PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(query.toString(), typesFinal);
			PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(params);
			getJdbcTemplate().update(psc);
		}
	}
	
	public void updateUserWithRoles(final UserWithRoles userWithRoles) {
		if (userWithRoles == null) {
			throw new IllegalArgumentException("'userWithRoles' parameter must not be null");
		}
		if (userWithRoles.isRolesModified()) {
			deleteUserRoles(userWithRoles.getUser());
			addUserRoles(userWithRoles);
		}
		updateUser(userWithRoles.getUser(), userWithRoles.isRolesModified());

	}
	
	public void updateUser(final User user) {
		updateUser(user, false);
	}
	
	// Optimistic lock check before updating.
	private Date getUserLastModifiedDate(final Integer userId) {
		if (userId == null) {
			throw new IllegalArgumentException("'userId' parameter must not be null");
		}
		SingleColumnRowMapper dateRowMapper = new SingleColumnRowMapper(Date.class);
		List results = getJdbcTemplate().query("select last_modified_date from users where id = ?", new Object[]{userId}, new int[]{Types.INTEGER}, dateRowMapper);
		if (results.size() != 1) {
			throw new DataIntegrityViolationException("Should have found only one row for user ID " + userId.toString());
		}
		Date lastModifiedDate = (Date)results.get(0);
		return lastModifiedDate;		
	}
		

//	public void updateLastLoginDate(final String username, final Date newLastLoginDate) {
//		if (!StringUtils.hasText(username)) {
//			throw new IllegalArgumentException("'username' parameter must not be null");
//		}
//		else if (newLastLoginDate == null) {
//			throw new IllegalArgumentException("'newLastLoginDate' parameter must not be null");
//		}
//		PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory("update users set last_login = ? where username = ?", new int[]{Types.TIMESTAMP, Types.VARCHAR});
//		PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(new Object[]{
//				newLastLoginDate, 
//				username});
//		getJdbcTemplate().update(psc);
//	}
//	
//	public void updatePassword(final String username, final String newPassword) {
//		if (!StringUtils.hasText(username)) {
//			throw new IllegalArgumentException("'username' parameter must not be null");
//		}
//		else if (!StringUtils.hasText(newPassword)) {
//			throw new IllegalArgumentException("'newLastLoginDate' parameter must not be null");
//		}
//		PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory("update users set password = ? where username = ?", new int[]{Types.VARCHAR, Types.VARCHAR});
//		PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(new Object[]{
//				newPassword, 
//				username});
//		getJdbcTemplate().update(psc);
//	}
	
	public void setJdbcDaoUtils(final JdbcDaoUtils jdbcDaoUtils) {
		this.jdbcDaoUtils = jdbcDaoUtils;
	}
}
