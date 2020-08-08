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
@Table(name = "course")
public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id", unique = true, nullable = false)
	private Long id;
	private String name;
	private String department;
	private int noCredits;
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Registration> registeryEntries = new HashSet<Registration>();

	public Course() {
		this(-1L, "N/A", "N/A", 0);
	}
	
	public Course(Long id, String name, String department, int noCredits) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.noCredits = noCredits;
	}

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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getNoCredits() {
		return noCredits;
	}

	public void setNoCredits(int noCredits) {
		this.noCredits = noCredits;
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
		return "Course [id=" + id + ", name=" + name + ", department=" + department + ", noCredits=" + noCredits + "]";
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        Course toCompare = (Course) obj;
        return id.equals(toCompare.id);
    }
	
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
