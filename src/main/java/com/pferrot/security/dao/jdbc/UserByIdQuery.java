package com.pferrot.security.dao.jdbc;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;

public class UserByIdQuery extends UserQuery {

	private static String SQL_USER_QUERY = "select id, username, password, enabled, creation_date, last_login_date, last_modified_date from users where id = ?";
	
	public UserByIdQuery(final DataSource ds) {
		super(ds, SQL_USER_QUERY);
		declareParameter(new SqlParameter("id", Types.INTEGER));
		compile();
	}
}
