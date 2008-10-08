package com.pferrot.security.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Role implements Serializable {
	
	
	private Integer id;
	private String name;
	
	/**
	 * The admin role.
	 */
	private static String ADMIN_NAME = "ADMIN";
	private static Integer ADMIN_ID = new Integer(1);
	public static Role ADMIN = null;
	
	/**
	 * The user role.
	 */
	private static String USER_NAME = "USER";
	private static Integer USER_ID = new Integer(2);
	public static Role USER = null;
	
	private static Map<Integer, Role> ID_VS_ROLE = new HashMap<Integer, Role>();
	private static Map<String, Role> NAME_VS_ROLE = new HashMap<String, Role>();
	
	static {
		ADMIN = new Role(ADMIN_ID, ADMIN_NAME);
		USER = new Role(USER_ID, USER_NAME);
		
		ID_VS_ROLE.put(ADMIN.getId(), ADMIN);
		NAME_VS_ROLE.put(ADMIN.getName(), ADMIN);
		
		ID_VS_ROLE.put(USER.getId(), USER);
		NAME_VS_ROLE.put(USER.getName(), USER);
	}
	
	private Role() {
		super();
	}
	
	public static Role getRole(final Integer id) {
		return ID_VS_ROLE.get(id);
	}
	
	public static Role getRole(final String name) {
		return NAME_VS_ROLE.get(name);
	}

	public Role(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
