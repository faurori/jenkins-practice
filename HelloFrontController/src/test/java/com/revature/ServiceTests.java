package com.revature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.model.Employee;
import com.revature.repositories.EmployeeDAO;
import com.revature.repositories.EmployeeDAOImpl;
import com.revature.service.EmployeeService;

public class ServiceTests {
	
	
	// Our Service layer depends on the DAO 
	
	private EmployeeDAO edaoImp;
	
	// Before every test method is run, do this:
	@Before
	public void setup() {
		
		edaoImp = mock(EmployeeDAOImpl.class); // Reflection at work!
		
		// set the edao of the Employee Service class = the mocked dao
		EmployeeService.edao = edaoImp; // I changed the visibility of edao in EmployeeService
		
		
	}
	
	// Happy path Scenario test
	@Test
	public void test_Employee_findByUsername() {
		
		Employee sampleEmp = new Employee(1, "a", "b", "c", "d" );
		Employee sampleEmp2 = new Employee(2, "e", "f", "g", "h" );
		
		List<Employee> list = new ArrayList<Employee>();
		list.add(sampleEmp);
		list.add(sampleEmp2);
		
		// We program our dao to return that data as its fake DB data
		when(edaoImp.findAll()).thenReturn(list);
		
		String dummyusername = sampleEmp.getUsername();
		
		// findByUsername() method in the service class returns the fetched user!
		Employee returned = EmployeeService.findByUsername(dummyusername);
		
		assertEquals(sampleEmp, returned);
	}
	
	/*
	 * Here we are testing to make sure that our findByUsername() Service 
	 * method returns null if emp doesn't exist in the "db" which is mocked by mockitdo
	 */
	@Test
	public void employeeNOtPresentInDb() {
		
		List<Employee> emptyList = new ArrayList<Employee>();
		
		when(edaoImp.findAll()).thenReturn(emptyList);
		
		Employee empFoundByUnsername = EmployeeService.findByUsername("test");
		
		// in our logic we said that the findByUsername should return NULL if the Emp doesn't exist
		assertNull(empFoundByUnsername);
	}

}














