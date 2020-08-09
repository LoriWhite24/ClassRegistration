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
import javax.validation.constraints.Pattern;

/**
 * The model for Student.
 * @author Lori White and Tara Kelly
 * @version v2 (08/08/2020)
 */
@Entity
@Table(name = "student")
public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id", unique = true, nullable = false)
	private Long id;
	@NotBlank
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
	@Column(unique = true)
	private String email;
	@NotBlank
	@Pattern(regexp = "^[aA-zZ]\\\\w{5, 29}$")
	private String username;
	@NotBlank
	@Pattern(regexp = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,}$")
	private String password;
	@Column(columnDefinition = "default 0")
	private Integer creditHours;
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Registration> registryEntries = new HashSet<Registration>();

	/**
	 * The default constructor.
	 * @author Tara Kelly
	 */
	public Student() {
		this("N/A", "N/A", "N/A", 0);
	}
	/**
	 * The overloaded constructor.
	 * @author Tara Kelly
	 * @param email the student's email
	 * @param username the student's user name
	 * @param password the student's password
	 * @param creditHours the number of credit hours the student is registered for 
	 */
	public Student(@NotBlank @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$") String email, @NotBlank @Pattern(regexp = "^[aA-zZ]\\\\w{5, 29}$") String username, @NotBlank @Pattern(regexp = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,}$") String password, Integer creditHours) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.creditHours = creditHours;
	}
	/**
	 * Retrieves the student id.
	 * @author Tara Kelly
	 * @return Long - the student id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Retrieves the student's email.
	 * @author Tara Kelly
	 * @return String - the student's email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Updates the student's email.
	 * @author Tara Kelly
	 * @param email the student's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Retrieves the student's user name.
	 * @author Tara Kelly
	 * @return String - the student's user name
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Updates the student's user name.
	 * @author Tara Kelly
	 * @param username the student's user name
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Retrieves the student's password.
	 * @author Tara Kelly
	 * @return String - the student's password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Updates the student's password.
	 * @author Tara Kelly
	 * @param password the student's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Retrieves the number of credit hours the student is registered for.
	 * @author Tara Kelly
	 * @return Integer - the number of credit hours the student is registered for
	 */
	public Integer getCreditHours() {
		return creditHours;
	}
	/**
	 * Updates the number of credit hours the student is registered for.
	 * @author Tara Kelly
	 * @param creditHours the number of credit hours the student is registered for
	 */
	public void setCreditHours(Integer creditHours) {
		this.creditHours = creditHours;
	}
	/**
	 * Retrieves the registry entries that are associated with this student.
	 * @author Tara Kelly
	 * @return Set - the registry entries that are associated with this student
	 */
	public Set<Registration> getRegistryEntries() {
		return registryEntries;
	}
	/**
	 * Updates the registry entries that are associated with this student.
	 * @author Tara Kelly
	 * @param registryEntries the registry entries that are associated with this student
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
	 * Creates a string representation of a student.
	 * @author Tara Kelly
	 * @return String - a string representation of a student
	 */
	@Override
	public String toString() {
		return "Student [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", creditHours=" + creditHours + "]";
	}
	/**
	 * Equals method for this class.
	 * @author Lori White
	 * @param obj the object to compare 
	 * @return boolean - whether the Students are equal
	 */
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        Student toCompare = (Student) obj;
        return id.equals(toCompare.id);
    }
	/**
	 * Creates a hash key for a student.
	 * @author Lori White
	 * @return int - the hash key value
	 */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
