package com.pferrot.security.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;

import com.pferrot.security.model.User;

public abstract class UserQuery extends MappingSqlQuery {
	
	public UserQuery(DataSource ds, String sql) {
		super(ds, sql);
	}

	@Override
	protected Object mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		Timestamp lastLoginTimeStamp = rs.getTimestamp("last_login_date");
		Date lastLoginDate = null;
		if (lastLoginTimeStamp != null) {
			lastLoginDate = new Date(lastLoginTimeStamp.getTime());
		}
		User user = new User(
				rs.getInt("id"), 
				rs.getString("username"), 
				rs.getString("password"), 
				rs.getBoolean("enabled"), 
				lastLoginDate,
				new Date(rs.getTimestamp("creation_date").getTime()),
				new Date(rs.getTimestamp("last_modified_date").getTime()));
		return user;
	}	
}
