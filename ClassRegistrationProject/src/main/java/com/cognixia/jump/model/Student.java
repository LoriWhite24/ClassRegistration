package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id", unique = true, nullable = false)
	private Long id;
	private String email;
	private String username;
	private String password;
	private int creditHours;
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Registration> registeryEntries = new HashSet<Registration>();

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

	public Set<Registration> getRegisteryEntries() {
		return registeryEntries;
	}

	public void setRegisteryEntries(Set<Registration> registeryEntries) {
		this.registeryEntries = registeryEntries;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", creditHours=" + creditHours + "]";
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        Student toCompare = (Student) obj;
        return id.equals(toCompare.id);
    }
	
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
