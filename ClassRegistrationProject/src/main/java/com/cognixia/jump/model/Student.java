package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Pattern;

/**
 * The model for Student.
 * @author Lori White and Tara Kelly
 * @version v4 (08/10/2020)
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
	//@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
	@Column(unique = true)
	private String email;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	//@Pattern(regexp = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,}$")
	private String password;
	
	@Column(columnDefinition = "int default 0")
	private Integer creditHours;

	/**
	 * The default constructor.
	 * @author Lori White and Tara Kelly
	 */
	public Student() {
		this("N/A", "N/A", "N/A", "N/A", 0);
	}
	/**
	 * The overloaded constructor.
	 * @author Lori White and Tara Kelly
	 * @param email the student's email
	 * @param firstName the student's first name
	 * @param lastName the student's last name
	 * @param password the student's password
	 * @param creditHours the number of credit hours the student is registered for 
	 */
	public Student(@NotBlank /*@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")*/ String email, 
					@NotBlank String firstName, @NotBlank String lastName,  @NotBlank /*@Pattern(regexp = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,}$")*/ String password, Integer creditHours) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
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
	 * Retrieves the student's first name.
	 * @author Lori White
	 * @return String - the student's first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Updates the student's first name.
	 * @author Lori White
	 * @param firstName the student's first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Retrieves the student's last name.
	 * @author Lori White
	 * @return String - the student's larst name
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Updates the student's last name.
	 * @author Lori White
	 * @param lastName the student's last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * Retrieves the serial version UID for this class.
	 * @author Tara Kelly
	 * @return long - the serial version UID for this class
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * Creates a string representation of a student.
	 * @author Lori White and Tara Kelly
	 * @return String - a string representation of a student
	 */
	@Override
	public String toString() {
		return "Student [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", creditHours=" + creditHours + "]";
	}
}
