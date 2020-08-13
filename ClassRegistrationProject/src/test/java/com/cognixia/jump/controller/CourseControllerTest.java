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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.cognixia.jump.exception.ResourceAlreadyExistsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Course;
import com.cognixia.jump.repository.CourseRepository;

/**
 * The Course Controller Test class.
 * @author Lori White
 * @version v1 (08/13/2020)
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {
	private final String STARTING_URI = "http://localhost:8090/api";
	
	@MockBean
	private CourseRepository repo;
	
	CourseController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	/**
	 * Tests the get request for retrieving all the courses in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetAllCourses() throws Exception {
		String uri = STARTING_URI + "/courses";
		List<Course> allCourses = Arrays.asList(new Course("name1", "department1", 3), new Course("name2", "math", 3));
		
		
		when( repo.findAll() ).thenReturn(allCourses);
		
		mockMvc.perform( get(uri) )
			.andDo(print())  
			.andExpect( status().isOk() )  
			.andExpect( jsonPath("$.length()").value( allCourses.size() ) ) 
			.andExpect( jsonPath("$[0].id").value(allCourses.get(0).getId()) )
			.andExpect( jsonPath("$[0].name").value(allCourses.get(0).getName()))
			.andExpect( jsonPath("$[0].department").value(allCourses.get(0).getDepartment()))
			.andExpect( jsonPath("$[0].noCredits").value(allCourses.get(0).getNoCredits()))
			.andExpect( jsonPath("$[1].id").value(allCourses.get(1).getId()) )
			.andExpect( jsonPath("$[1].name").value(allCourses.get(1).getName()))
			.andExpect( jsonPath("$[1].department").value(allCourses.get(1).getDepartment()))
			.andExpect( jsonPath("$[1].noCredits").value(allCourses.get(1).getNoCredits()));
			
		verify(repo, times(1)).findAll();
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for retrieving the course by id in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetCourseById() throws Exception {
		String uri = STARTING_URI + "/courses/{id}";
		long id = 1;
		Course course = new Course("name1", "department1", 3);
		
		when( repo.findById(id) ).thenReturn(Optional.of(course));
		
		mockMvc.perform( get(uri, id) )
			.andExpect( status().isOk() )
			.andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
			.andExpect( jsonPath("$.id").value(course.getId()) )
			.andExpect( jsonPath("$.name").value(course.getName()) )
			.andExpect( jsonPath("$.department").value(course.getDepartment() ) )
			.andExpect( jsonPath("$.noCredits").value(course.getNoCredits() ) );
		
		verify(repo, times(1)).findById(id);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for not retrieving the course by id when the course does not exist in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetCourseNotFoundById() throws Exception {
		String uri = STARTING_URI + "/courses/{id}";
		long id = 3;
		
		when( repo.findById(id) ).thenReturn(Optional.empty());
		
		mockMvc.perform( get(uri, id) )
			.andExpect( status().isNotFound() )
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(result -> assertEquals("Course with id = " + id + " does not exist.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).findById(id);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the post request for a course being added to the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testAddCourse() throws Exception {
		String uri = STARTING_URI + "/add/course";
		
		Course courseCreated = new Course("name1", "department1", 3);
		
		when(repo.existsById(courseCreated.getId())).thenReturn(false);
		when( repo.save( Mockito.any(Course.class) ) ).thenReturn(courseCreated);
		
		mockMvc.perform( post(uri)
						.contentType( MediaType.APPLICATION_JSON ) 
						.content( ClassRegistrationApplicationTests.asJsonString(courseCreated) ) )
				.andDo(print() )
				.andExpect( status().isCreated() )
				.andExpect( jsonPath("$.id").value(courseCreated.getId()) )
				.andExpect( jsonPath("$.name").value(courseCreated.getName()) )
				.andExpect( jsonPath("$.department").value(courseCreated.getDepartment() ) )
				.andExpect( jsonPath("$.noCredits").value(courseCreated.getNoCredits() ) );
		
		verify(repo, times(1)).existsById(courseCreated.getId());
		verify(repo, times(1)).save(Mockito.any(Course.class));
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the post request for a course not being added when the course already exists in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testAddCourseThatExists() throws Exception {
		String uri = STARTING_URI + "/add/course";
		
		Course courseCreated = new Course("name1", "department1", 3);
		
		when(repo.existsById(courseCreated.getId())).thenReturn(true);
		
		mockMvc.perform( post(uri)
						.contentType( MediaType.APPLICATION_JSON ) 
						.content( ClassRegistrationApplicationTests.asJsonString(courseCreated) ) )
				.andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException))
				.andExpect(result -> assertEquals("Course with id = " + courseCreated.getId() + " already exists.", result.getResolvedException().getMessage()));
		
		verify(repo, times(1)).existsById(courseCreated.getId());
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for retrieving all the courses by department in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetCoursesByDepartment() throws Exception {
		String uri = STARTING_URI + "/courses/department/{department}";
		String department = "Science";
		List<Course> allCourses = Arrays.asList(new Course("name1", "Computer Science", 3), new Course("name2", "department1", 3), new Course("name3", "Political Science", 3));
		List<Course> expectedOutput = new ArrayList<Course>();
		expectedOutput.addAll(allCourses);
		expectedOutput.remove(1);
		
		when(repo.findByDepartmentContaining(department)).thenReturn(expectedOutput);
		
		mockMvc.perform(get(uri, department))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(allCourses.size() - 1))
			.andExpect(jsonPath("$[0].id").value(allCourses.get(0).getId()))
			.andExpect(jsonPath("$[0].name").value(allCourses.get(0).getName()))
			.andExpect(jsonPath("$[0].department").value(allCourses.get(0).getDepartment()))
			.andExpect(jsonPath("$[0].noCredits").value(allCourses.get(0).getNoCredits()))
			.andExpect(jsonPath("$[1].id").value(allCourses.get(2).getId()))
			.andExpect(jsonPath("$[1].name").value(allCourses.get(2).getName()))
			.andExpect(jsonPath("$[1].department").value(allCourses.get(2).getDepartment()))
			.andExpect(jsonPath("$[1].noCredits").value(allCourses.get(2).getNoCredits()));
		
		verify(repo, times(1)).findByDepartmentContaining(department);
		verifyNoMoreInteractions(repo);
	}
	/**
	 * Tests the get request for retrieving all the courses by name in the database.
	 * @author Lori White
	 * @throws Exception is thrown when there is an error in the test
	 */
	@Test
	void testGetCoursesByName() throws Exception {
		String uri = STARTING_URI + "/courses/name/{name}";
		String name = "Calculus";
		List<Course> allCourses = Arrays.asList(new Course("Calculus I", "math", 3), new Course("Course Name", "department1", 3), new Course("Calculus II", "math", 3));
		List<Course> expectedOutput = new ArrayList<Course>();
		expectedOutput.addAll(allCourses);
		expectedOutput.remove(1);
		
		when(repo.findByNameContaining(name)).thenReturn(expectedOutput);
		
		mockMvc.perform(get(uri, name))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(allCourses.size() - 1))
			.andExpect(jsonPath("$[0].id").value(allCourses.get(0).getId()))
			.andExpect(jsonPath("$[0].name").value(allCourses.get(0).getName()))
			.andExpect(jsonPath("$[0].department").value(allCourses.get(0).getDepartment()))
			.andExpect(jsonPath("$[0].noCredits").value(allCourses.get(0).getNoCredits()))
			.andExpect(jsonPath("$[1].id").value(allCourses.get(2).getId()))
			.andExpect(jsonPath("$[1].name").value(allCourses.get(2).getName()))
			.andExpect(jsonPath("$[1].department").value(allCourses.get(2).getDepartment()))
			.andExpect(jsonPath("$[1].noCredits").value(allCourses.get(2).getNoCredits()));
		
		verify(repo, times(1)).findByNameContaining(name);
		verifyNoMoreInteractions(repo);
	}
}
