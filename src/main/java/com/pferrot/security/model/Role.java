package com.pferrot.security.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
public class Role {
	
	@Id @GeneratedValue
	@Column(name = "ID")	
	private Long id;
	
	@Column(name = "NAME", nullable = false, length = 20)
	private String name;
	
	
//	@JoinTable(name = "USERS_ROLES",
//	           joinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"),
//	           inverseJoinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID")
//	           )
	@ManyToMany(targetEntity = com.pferrot.security.model.User.class)	
	private Collection<User> users;
	
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

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}
}
