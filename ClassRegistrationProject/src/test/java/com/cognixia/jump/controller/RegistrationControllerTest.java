package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.cognixia.jump.exception.InvalidHasWithdrawnUpdateException;
import com.cognixia.jump.exception.ResourceAlreadyExistsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Registration;
import com.cognixia.jump.model.RegistrationHasWithdrawnOnly;
import com.cognixia.jump.repository.RegistrationRepository;

/**
 * The Registration Controller Test class.
 * @author Lori White
 * @version v1 (08/13/2020)
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {
	private final String STARTING_URI = "http://localhost:8090/api";
	
	@MockBean
	private RegistrationRepository repo;
	
	RegistrationController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	/**
	 * Tests the get request for retrieving all the registrations by student id in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetRegistrationsByStudentId() throws Exception {
		String uri = STARTING_URI + "/registration/student/{studentId}";
		long studentId = 1;
		List<Registration> allRegistrations = Arrays.asList(new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1")), new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("2"), Long.parseLong("1")), new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("2")));
		List<Registration> expectedOutput = new ArrayList<Registration>();
		expectedOutput.addAll(allRegistrations);
		expectedOutput.remove(1);
		
		when(repo.existsByStudentId(studentId)).thenReturn(true);
		when(repo.findByStudentId(studentId)).thenReturn(expectedOutput);
		
		mockMvc.perform(get(uri, studentId))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(allRegistrations.size() - 1))
			.andExpect(jsonPath("$[0].id").value(allRegistrations.get(0).getId()))
			.andExpect(jsonPath("$[0].registrationDate").value(allRegistrations.get(0).getRegistrationDate().toString()))
			.andExpect(jsonPath("$[0].hasWithdrawn").value(allRegistrations.get(0).getHasWithdrawn()))
			.andExpect(jsonPath("$[0].studentId").value(allRegistrations.get(0).getStudentId()))
			.andExpect(jsonPath("$[0].courseId").value(allRegistrations.get(0).getCourseId()))
			.andExpect(jsonPath("$[1].id").value(allRegistrations.get(2).getId()))
			.andExpect(jsonPath("$[1].registrationDate").value(allRegistrations.get(2).getRegistrationDate().toString()))
			.andExpect(jsonPath("$[1].hasWithdrawn").value(allRegistrations.get(2).getHasWithdrawn()))
			.andExpect(jsonPath("$[1].studentId").value(allRegistrations.get(2).getStudentId()))
			.andExpect(jsonPath("$[1].courseId").value(allRegistrations.get(2).getCourseId()));
		
		verify(repo, times(1)).existsByStudentId(studentId);
		verify(repo, times(1)).findByStudentId(studentId);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for not retrieving all the registrations by student id when the student id can not be found in registration from the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetRegistrationsNotFoundByStudentId() throws Exception {
		String uri = STARTING_URI + "/registration/student/{studentId}";
		long studentId = 3;
		List<Registration> allRegistrations = Arrays.asList(new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1")), new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("2"), Long.parseLong("1")), new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("2")));
		List<Registration> expectedOutput = new ArrayList<Registration>();
		expectedOutput.addAll(allRegistrations);
		expectedOutput.remove(1);
		
		when(repo.existsByStudentId(studentId)).thenReturn(false);
		
		mockMvc.perform(get(uri, studentId))
			.andExpect(status().isNotFound())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(result -> assertEquals("Registry entries with student id = " + studentId + " does not exist.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsByStudentId(studentId);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for retrieving all the registrations by course id in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetRegistrationsByCourseId() throws Exception {
		String uri = STARTING_URI + "/registration/course/{courseId}";
		long courseId = 1;
		List<Registration> allRegistrations = Arrays.asList(new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1")), new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("2")), new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("2"), Long.parseLong("1")));
		List<Registration> expectedOutput = new ArrayList<Registration>();
		expectedOutput.addAll(allRegistrations);
		expectedOutput.remove(1);
		
		when(repo.existsByCourseId(courseId)).thenReturn(true);
		when(repo.findByCourseId(courseId)).thenReturn(expectedOutput);
		
		mockMvc.perform(get(uri, courseId))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(allRegistrations.size() - 1))
			.andExpect(jsonPath("$[0].id").value(allRegistrations.get(0).getId()))
			.andExpect(jsonPath("$[0].registrationDate").value(allRegistrations.get(0).getRegistrationDate().toString()))
			.andExpect(jsonPath("$[0].hasWithdrawn").value(allRegistrations.get(0).getHasWithdrawn()))
			.andExpect(jsonPath("$[0].studentId").value(allRegistrations.get(0).getStudentId()))
			.andExpect(jsonPath("$[0].courseId").value(allRegistrations.get(0).getCourseId()))
			.andExpect(jsonPath("$[1].id").value(allRegistrations.get(2).getId()))
			.andExpect(jsonPath("$[1].registrationDate").value(allRegistrations.get(2).getRegistrationDate().toString()))
			.andExpect(jsonPath("$[1].hasWithdrawn").value(allRegistrations.get(2).getHasWithdrawn()))
			.andExpect(jsonPath("$[1].studentId").value(allRegistrations.get(2).getStudentId()))
			.andExpect(jsonPath("$[1].courseId").value(allRegistrations.get(2).getCourseId()));
		
		verify(repo, times(1)).existsByCourseId(courseId);
		verify(repo, times(1)).findByCourseId(courseId);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for not retrieving all the registrations by course id when the course id can not be found in registration from the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetRegistrationsNotFoundByCourseId() throws Exception {
		String uri = STARTING_URI + "/registration/course/{courseId}";
		long courseId = 3;
		List<Registration> allRegistrations = Arrays.asList(new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1")), new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("2")), new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("2"), Long.parseLong("1")));
		List<Registration> expectedOutput = new ArrayList<Registration>();
		expectedOutput.addAll(allRegistrations);
		expectedOutput.remove(1);
		
		when(repo.existsByCourseId(courseId)).thenReturn(false);
		
		mockMvc.perform(get(uri, courseId))
			.andExpect(status().isNotFound())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(result -> assertEquals("Registry entries with course id = " + courseId + " does not exist.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsByCourseId(courseId);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the patch request for withdrawing a student from a course successfully.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testValidWithdraw() throws Exception {
		String uri = STARTING_URI + "/update/registration/has_withdrawn";
		
		RegistrationHasWithdrawnOnly registrationHasWithdrawnOnly = new RegistrationHasWithdrawnOnly(Long.parseLong("1"), Long.parseLong("1"), true);
		Registration registration = new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1"));
		Registration expectedRegistration = new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), true, Long.parseLong("1"), Long.parseLong("1"));
		
		when(repo.existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId())).thenReturn(true);
		when(repo.findByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId())).thenReturn(registration);
		when(repo.save(Mockito.any(Registration.class))).thenReturn(expectedRegistration);
		
		mockMvc.perform(patch(uri)
						.contentType( MediaType.APPLICATION_JSON ) 
						.content( ClassRegistrationApplicationTests.asJsonString(expectedRegistration) ) )
			.andDo(print() )
			.andExpect( status().isAccepted() )
			.andExpect( jsonPath("$.id").value(expectedRegistration.getId()) )
			.andExpect( jsonPath("$.registrationDate").value(expectedRegistration.getRegistrationDate().toString()) )
			.andExpect( jsonPath("$.hasWithdrawn").value(expectedRegistration.getHasWithdrawn() ) )
			.andExpect( jsonPath("$.studentId").value(expectedRegistration.getStudentId() ) )
			.andExpect( jsonPath("$.courseId").value(expectedRegistration.getCourseId()) );
		
		verify(repo, times(1)).existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId());
		verify(repo, times(1)).findByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId());
		verify(repo, times(1)).save(Mockito.any(Registration.class));
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the patch request for not withdrawing or not re-enrolling a student from a course because the student id and course id provided could not be found in registration from the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testInvalidHasWithdrawnByNotFound() throws Exception {
		String uri = STARTING_URI + "/update/registration/has_withdrawn";
		
		RegistrationHasWithdrawnOnly registrationHasWithdrawnOnly = new RegistrationHasWithdrawnOnly(Long.parseLong("2"), Long.parseLong("1"), true);
		
		when(repo.existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId())).thenReturn(false);
		
		mockMvc.perform(patch(uri)
				.contentType( MediaType.APPLICATION_JSON ) 
				.content( ClassRegistrationApplicationTests.asJsonString(registrationHasWithdrawnOnly) ) )
			.andExpect( status().isNotFound() )
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
		    .andExpect(result -> assertEquals("Registry Entry with student id = " + registrationHasWithdrawnOnly.getStudentId() + " and course id = " + registrationHasWithdrawnOnly.getCourseId() + " doesn't exist.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId());
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the patch request for not withdrawing a student from a course because the student has already withdrawn from the course.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testInvalidWithdrawByAlreadyWithdrawn() throws Exception {
		String uri = STARTING_URI + "/update/registration/has_withdrawn";
		
		RegistrationHasWithdrawnOnly registrationHasWithdrawnOnly = new RegistrationHasWithdrawnOnly(Long.parseLong("1"), Long.parseLong("1"), true);
		Registration registration = new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), true, Long.parseLong("1"), Long.parseLong("1"));
		
		when(repo.existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId())).thenReturn(true);
		when(repo.findByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId())).thenReturn(registration);
		
		mockMvc.perform(patch(uri)
				.contentType( MediaType.APPLICATION_JSON ) 
				.content( ClassRegistrationApplicationTests.asJsonString(registrationHasWithdrawnOnly) ) )
			.andExpect( status().isBadRequest() )
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidHasWithdrawnUpdateException))
		    .andExpect(result -> assertEquals("Can not withdraw when not currently registered.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId());
		verify(repo, times(1)).findByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId());
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the patch request for re-enrolling a student to a course successfully.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testValidReEnroll() throws Exception {
		String uri = STARTING_URI + "/update/registration/has_withdrawn";
		
		RegistrationHasWithdrawnOnly registrationHasWithdrawnOnly = new RegistrationHasWithdrawnOnly(Long.parseLong("1"), Long.parseLong("1"), false);
		Registration registration = new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), true, Long.parseLong("1"), Long.parseLong("1"));
		Registration expectedRegistration = new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1"));
		
		when(repo.existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId())).thenReturn(true);
		when(repo.findByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId())).thenReturn(registration);
		when(repo.save(Mockito.any(Registration.class))).thenReturn(expectedRegistration);
		
		mockMvc.perform(patch(uri)
						.contentType( MediaType.APPLICATION_JSON ) 
						.content( ClassRegistrationApplicationTests.asJsonString(expectedRegistration) ) )
			.andDo(print() )
			.andExpect( status().isAccepted() )
			.andExpect( jsonPath("$.id").value(expectedRegistration.getId()) )
			.andExpect( jsonPath("$.registrationDate").value(expectedRegistration.getRegistrationDate().toString()) )
			.andExpect( jsonPath("$.hasWithdrawn").value(expectedRegistration.getHasWithdrawn() ) )
			.andExpect( jsonPath("$.studentId").value(expectedRegistration.getStudentId() ) )
			.andExpect( jsonPath("$.courseId").value(expectedRegistration.getCourseId()) );
		
		verify(repo, times(1)).existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId());
		verify(repo, times(1)).findByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId());
		verify(repo, times(1)).save(Mockito.any(Registration.class));
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the patch request for not re-enrolling a student from a course because the student has already re-enrolled to the course.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testInvalidReEnrollByAlreadyEnrolled() throws Exception {
		String uri = STARTING_URI + "/update/registration/has_withdrawn";
		
		RegistrationHasWithdrawnOnly registrationHasWithdrawnOnly = new RegistrationHasWithdrawnOnly(Long.parseLong("1"), Long.parseLong("1"), false);
		Registration registration = new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1"));
		
		when(repo.existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId())).thenReturn(true);
		when(repo.findByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId())).thenReturn(registration);
		
		mockMvc.perform(patch(uri)
				.contentType( MediaType.APPLICATION_JSON ) 
				.content( ClassRegistrationApplicationTests.asJsonString(registrationHasWithdrawnOnly) ) )
			.andExpect( status().isBadRequest() )
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidHasWithdrawnUpdateException))
		    .andExpect(result -> assertEquals("Can not re-enroll when currently registered.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId());
		verify(repo, times(1)).findByStudentIdandCourseId(registrationHasWithdrawnOnly.getStudentId(), registrationHasWithdrawnOnly.getCourseId());
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the post request for a registry entry being added to the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testAddRegistration() throws Exception {
		String uri = STARTING_URI + "/add/registration";
		
		Registration registrationCreated = new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1"));
		
		when(repo.existsById(registrationCreated.getId())).thenReturn(false);
		when(repo.existsByStudentIdandCourseId(registrationCreated.getStudentId(), registrationCreated.getCourseId())).thenReturn(false);
		when( repo.save( Mockito.any(Registration.class) ) ).thenReturn(registrationCreated);
		
		mockMvc.perform( post(uri)
						.contentType( MediaType.APPLICATION_JSON ) 
						.content( ClassRegistrationApplicationTests.asJsonString(registrationCreated) ) )
				.andDo(print() )
				.andExpect( status().isCreated() )
				.andExpect( jsonPath("$.id").value(registrationCreated.getId()) )
				.andExpect( jsonPath("$.registrationDate").value(registrationCreated.getRegistrationDate().toString()) )
				.andExpect( jsonPath("$.hasWithdrawn").value(registrationCreated.getHasWithdrawn() ) )
				.andExpect( jsonPath("$.studentId").value(registrationCreated.getStudentId() ) )
				.andExpect( jsonPath("$.courseId").value(registrationCreated.getCourseId()) );
		
		verify(repo, times(1)).existsById(registrationCreated.getId());
		verify(repo, times(1)).existsByStudentIdandCourseId(registrationCreated.getStudentId(), registrationCreated.getCourseId());
		verify(repo, times(1)).save(Mockito.any(Registration.class));
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the post request for a registry entry not being added when the registry entry already exists in the database based on id.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testAddRegistrationThatExistsById() throws Exception {
		String uri = STARTING_URI + "/add/registration";
		
		Registration registrationCreated = new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1"));
		
		when(repo.existsById(registrationCreated.getId())).thenReturn(true);
		
		mockMvc.perform( post(uri)
						.contentType( MediaType.APPLICATION_JSON ) 
						.content( ClassRegistrationApplicationTests.asJsonString(registrationCreated) ) )
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException))
				.andExpect(result -> assertEquals("Registry entry with id = " + registrationCreated.getId() + " already exists.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsById(registrationCreated.getId());
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the post request for a registry entry not being added when the registry entry already exists in the database based on student id and course id.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testAddRegistrationThatExistsByStudentIdAndCourseId() throws Exception {
		String uri = STARTING_URI + "/add/registration";
		
		Registration registrationCreated = new Registration(Date.valueOf(LocalDate.of(2000, 3, 12)), false, Long.parseLong("1"), Long.parseLong("1"));
		
		when(repo.existsById(registrationCreated.getId())).thenReturn(false);
		when(repo.existsByStudentIdandCourseId(registrationCreated.getStudentId(), registrationCreated.getCourseId())).thenReturn(true);
		
		mockMvc.perform( post(uri)
						.contentType( MediaType.APPLICATION_JSON ) 
						.content( ClassRegistrationApplicationTests.asJsonString(registrationCreated) ) )
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException))
				.andExpect(result -> assertEquals("Registry entry with student id = " + registrationCreated.getStudentId() + " and with course id = " + registrationCreated.getCourseId() + " already exists.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsById(registrationCreated.getId());
		verify(repo, times(1)).existsByStudentIdandCourseId(registrationCreated.getStudentId(), registrationCreated.getCourseId());
		verifyNoMoreInteractions(repo);
	}
}
