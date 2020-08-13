package com.cognixia.jump;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class Registration Application Test class.
 * @author Lori White
 * @version v1 (08/13/2020)
 */
@SpringBootTest
 public class ClassRegistrationApplicationTests {

	@Test
	void contextLoads() {
	}
	/**
	 * Converts the JSON Object into a String.
	 * @author Lori White
	 * @param obj the JSON Object to convert
	 * @return String - the String representation of the JSON Object
	 */
	public static String asJsonString(final Object obj) {
		
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch(Exception e) {
			throw new RuntimeException();
		}
		
	}
}
