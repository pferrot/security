package com.pferrot.security.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.pferrot.security.model.Role;

public class RolesByUserIdQuery extends MappingSqlQuery {

	private static String SQL_ROLES_QUERY = "select roles.id from roles, users_roles where " +
			"roles.id = users_roles.role_id and users_roles.user_id = ?";
	
	public RolesByUserIdQuery(final DataSource ds) {
		super(ds, SQL_ROLES_QUERY);
		declareParameter(new SqlParameter("user_id", Types.INTEGER));
		compile();
	}

	@Override
	protected Object mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		Integer id = rs.getInt("id");
		return Role.getRole(id);
	}	
}
