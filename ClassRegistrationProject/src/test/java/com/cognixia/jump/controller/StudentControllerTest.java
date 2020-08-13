package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.ClassRegistrationApplicationTests;
import com.cognixia.jump.exception.InvalidLoginException;
import com.cognixia.jump.exception.ResourceAlreadyExistsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Student;
import com.cognixia.jump.repository.StudentRepository;

/**
 * The Student Controller Test class.
 * @author Lori White
 * @version v1 (08/13/2020)
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
	private final String STARTING_URI = "http://localhost:8090/api";
	
	@MockBean
	private StudentRepository repo;
	
	StudentController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	/**
	 * Tests the get request for retrieving the student by id in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetStudentById() throws Exception {
		String uri = STARTING_URI + "/students/{id}";
		long id = 1;
		Student student = new Student("email1", "fname1", "lname1", "pass1", 0);
		
		when( repo.findById(id) ).thenReturn(Optional.of(student));
		
		mockMvc.perform( get(uri, id) )
			.andExpect( status().isOk() )
			.andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
			.andExpect( jsonPath("$.id").value(student.getId()) )
			.andExpect( jsonPath("$.email").value(student.getEmail()) )
			.andExpect( jsonPath("$.firstName").value(student.getFirstName() ) )
			.andExpect( jsonPath("$.lastName").value(student.getLastName() ) )
			.andExpect( jsonPath("$.password").value(student.getPassword() ) )
			.andExpect( jsonPath("$.creditHours").value(student.getCreditHours() ) );
		
		verify(repo, times(1)).findById(id);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for retrieving the student by id when the student does not exist in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetStudentNotFoundById() throws Exception {
		String uri = STARTING_URI + "/students/{id}";
		long id = 3;
		
		when( repo.findById(id) ).thenReturn(Optional.empty());
		
		mockMvc.perform( get(uri, id) )
			.andExpect( status().isNotFound() )
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(result -> assertEquals("Student with id = " + id + " does not exist.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).findById(id);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for verifying a student has logged in.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testValidLogin() throws Exception {
		String uri = STARTING_URI + "/students/login/username/{email}/password/{password}";
		String email = "email1", password = "pass1";
		Student student = new Student("email1", "fname1", "lname1", "pass1", 0);
		
		when(repo.existsByEmail(email)).thenReturn(student.getEmail().equals(email));
		when(repo.findByEmail(email)).thenReturn(student);
		
		mockMvc.perform( get(uri, email, password))
				.andExpect( status().isOk() )
				.andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
				.andExpect( jsonPath("$.id").value(student.getId()) )
				.andExpect( jsonPath("$.email").value(student.getEmail()) )
				.andExpect( jsonPath("$.firstName").value(student.getFirstName() ) )
				.andExpect( jsonPath("$.lastName").value(student.getLastName() ) )
				.andExpect( jsonPath("$.password").value(student.getPassword() ) )
				.andExpect( jsonPath("$.creditHours").value(student.getCreditHours() ) );
		
		verify(repo, times(1)).existsByEmail(email);
		verify(repo, times(1)).findByEmail(email);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for verifying a student has not logged in because the student does not exists in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testInvalidLoginByEmail() throws Exception {
		String uri = STARTING_URI + "/students/login/username/{email}/password/{password}";
		String email = "email", password = "pass1";
		Student student = new Student("email1", "fname1", "lname1", "pass1", 0);
		
		when(repo.existsByEmail(email)).thenReturn(student.getEmail().equals(email));
		
		mockMvc.perform( get(uri, email, password))
				.andExpect( status().isNotFound() )
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("Email is Invalid!", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsByEmail(email);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for verifying a student has not logged in because the student does exists in the database but their password does not match the one provided.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testInvalidLoginByPassword() throws Exception {
		String uri = STARTING_URI + "/students/login/username/{email}/password/{password}";
		String email = "email1", password = "pass";
		Student student = new Student("email1", "fname1", "lname1", "pass1", 0);
		
		when(repo.existsByEmail(email)).thenReturn(student.getEmail().equals(email));
		when(repo.findByEmail(email)).thenReturn(student);
		
		mockMvc.perform( get(uri, email, password))
				.andExpect( status().isUnauthorized() )
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidLoginException))
				.andExpect(result -> assertEquals("Password is Invalid!", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsByEmail(email);
		verify(repo, times(1)).findByEmail(email);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the post request for a student being added to the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testAddStudent() throws Exception {
		String uri = STARTING_URI + "/add/student";
		
		Student studentCreated = new Student("email1", "fname1", "lname1", "pass1", 0);
		
		when(repo.existsById(studentCreated.getId())).thenReturn(false);
		when( repo.save( Mockito.any(Student.class) ) ).thenReturn(studentCreated);
		
		mockMvc.perform( post(uri)
						.contentType( MediaType.APPLICATION_JSON ) 
						.content( ClassRegistrationApplicationTests.asJsonString(studentCreated) ) )
				.andDo(print() )
				.andExpect( status().isCreated() )
				.andExpect( jsonPath("$.id").value(studentCreated.getId()) )
				.andExpect( jsonPath("$.email").value(studentCreated.getEmail()) )
				.andExpect( jsonPath("$.firstName").value(studentCreated.getFirstName() ) )
				.andExpect( jsonPath("$.lastName").value(studentCreated.getLastName() ) )
				.andExpect( jsonPath("$.password").value(studentCreated.getPassword() ) )
				.andExpect( jsonPath("$.creditHours").value(studentCreated.getCreditHours() ) );
		
		verify(repo, times(1)).existsById(studentCreated.getId());
		verify(repo, times(1)).save(Mockito.any(Student.class));
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the post request for a student not being added when the student already exists in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testAddStudentThatExists() throws Exception {
		String uri = STARTING_URI + "/add/student";
		
		Student studentCreated = new Student("email1", "fname1", "lname1", "pass1", 0);
		
		when(repo.existsById(studentCreated.getId())).thenReturn(true);
		
		mockMvc.perform( post(uri)
						.contentType( MediaType.APPLICATION_JSON ) 
						.content( ClassRegistrationApplicationTests.asJsonString(studentCreated) ) )
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException))
				.andExpect(result -> assertEquals("Student with id = " + studentCreated.getId() + " already exists.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsById(studentCreated.getId());
		verifyNoMoreInteractions(repo);
	}
}
