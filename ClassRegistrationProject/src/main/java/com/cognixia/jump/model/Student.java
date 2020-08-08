package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String email;
	private String username;
	private String password;
	private int creditHours;

	public Student() {
		this(-1L, "N/A", "N/A", "N/A", 0);
	}
	
	public Student(Long id, String email, String username, String password, int creditHours) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.creditHours = creditHours;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(int creditHours) {
		this.creditHours = creditHours;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", creditHours=" + creditHours + "]";
	}
	
}
