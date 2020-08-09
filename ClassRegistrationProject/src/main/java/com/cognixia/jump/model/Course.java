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
import javax.validation.constraints.NotBlank;

/**
 * The model for Course.
 * @author Lori White and Tara Kelly
 * @version v2 (08/08/2020)
 */
@Entity
@Table(name = "course")
public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id", unique = true, nullable = false)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String department;
	@Column(columnDefinition = "default 3")
	private Integer noCredits;
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Registration> registryEntries = new HashSet<Registration>();
	
	/**
	 * The default constructor.
	 * @author Tara Kelly
	 */
	public Course() {
		this("N/A", "N/A", 0);
	}
	/**
	 * The overloaded constructor.
	 * @author Tara Kelly
	 * @param name the name of the course
	 * @param department the department the course is associated with
	 * @param noCredits the number of credits the course is worth
	 */
	public Course(@NotBlank String name, @NotBlank String department, Integer noCredits) {
		super();
		this.name = name;
		this.department = department;
		this.noCredits = noCredits;
	}
	/**
	 * Retrieves the course id.
	 * @author Tara Kelly
	 * @return Long - the course id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Retrieves the name of the course.
	 * @author Tara Kelly
	 * @return String - the name of the course
	 */
	public String getName() {
		return name;
	}
	/**
	 * Updates the name of the course.
	 * @author Tara Kelly
	 * @param name the name of the course
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Retrieves the department the course is associated with
	 * @author Tara Kelly
	 * @return String - the department the course is associated with
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * Updates the department the course is associated with.
	 * @author Tara Kelly
	 * @param department the department the course is associated with
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * Retrieves the number of credits the course is worth.
	 * @author Tara Kelly
	 * @return Integer - the number of credits the course is worth
	 */
	public Integer getNoCredits() {
		return noCredits;
	}
	/**
	 * Updates the number of credits the course is worth.
	 * @author Tara Kelly
	 * @param noCredits the number of credits the course is worth
	 */
	public void setNoCredits(Integer noCredits) {
		this.noCredits = noCredits;
	}
	/**
	 * Retrieves the registry entries that are associated with this course.
	 * @author Tara Kelly
	 * @return Set - the registry entries that are associated with this course
	 */
	public Set<Registration> getRegistryEntries() {
		return registryEntries;
	}
	/**
	 * Updates the registry entries that are associated with this course.
	 * @author Tara Kelly
	 * @param registryEntries the registry entries that are associated with this course
	 */
	public void setRegistryEntries(Set<Registration> registryEntries) {
		this.registryEntries = registryEntries;
	}
	/**
	 * Retrieves the serial version UID for this class.
	 * @author Tara Kelly
	 * @return long - the serial version UID for this class
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * Creates a string representation of a course.
	 * @author Tara Kelly
	 * @return String - a string representation of a course
	 */
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", department=" + department + ", noCredits=" + noCredits + "]";
	}
	/**
	 * Equals method for this class.
	 * @author Lori White
	 * @param obj the object to compare 
	 * @return boolean - whether the Courses are equal
	 */
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        Course toCompare = (Course) obj;
        return id.equals(toCompare.id);
    }
	/**
	 * Creates a hash key for a course.
	 * @author Lori White
	 * @return int - the hash key value
	 */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
