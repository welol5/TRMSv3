package dev.elliman.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.elliman.beans.Person;
import dev.elliman.beans.Role;
import dev.elliman.data.PersonDAO;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	
	@Mock
	private static PersonDAO personDAO;
	
	@InjectMocks
	private static PersonServiceImpl personServ;
	
	private static Person testPerson;
	
	@BeforeAll
	public static void beforeAll() {
		testPerson = new Person();
		testPerson.setId(0);
		testPerson.setUsername("test");
		testPerson.setPassword("password");
		testPerson.setFirstName("first");
		testPerson.setLastName("last");
		testPerson.setAmountClaimed(0D);
		Role role = new Role();
		role.setId(1);
		role.setName("role");
		testPerson.setRole(role);
	}
	
	@Test
	public void testLogin() {
		when(personDAO.getPersonByUsername(testPerson.getUsername())).thenReturn(testPerson);
		
		assertTrue(personServ.login(testPerson.getUsername(), testPerson.getPassword()).equals(testPerson));
		
		verify(personDAO).getPersonByUsername(testPerson.getUsername());
	}
	
	@Test
	public void testIncorrectPasswordLogin() {
		when(personDAO.getPersonByUsername(testPerson.getUsername())).thenReturn(testPerson);
		
		assertNull(personServ.login(testPerson.getUsername(), "wrongPassword"));
		
		verify(personDAO).getPersonByUsername(testPerson.getUsername());
	}
	
	@Test
	public void testUsernameNotFoundLogin() {
		when(personDAO.getPersonByUsername(testPerson.getUsername())).thenReturn(null);
		
		assertNull(personServ.login(testPerson.getUsername(), testPerson.getPassword()));
		
		verify(personDAO).getPersonByUsername(testPerson.getUsername());
	}
}
