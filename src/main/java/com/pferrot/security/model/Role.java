package com.pferrot.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "ROLES")
public class Role {
	public static final String USER_ROLE_NAME = "USER";
	public static final String ADMIN_ROLE_NAME = "ADMIN";
	
	@Id @GeneratedValue
	@Column(name = "ID")	
	private Long id;
	
	@Column(name = "NAME", unique = true, nullable = false, length = 20)
	@Audited
	private String name;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
