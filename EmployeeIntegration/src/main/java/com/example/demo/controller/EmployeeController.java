package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

public class EmployeeController {
	
	@Autowired
	private EmployeeRepository empReposiory;

	@PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee emp){
		try {
			Employee _emp=empReposiory.save(new Employee(emp.getFirstName(),emp.getLastName(),emp.getEmailId()));
			return new ResponseEntity<>(_emp,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/employees")
	public List<Employee> getEmployee(){
		return empReposiory.findAll();
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmpById(@PathVariable long id){
		Optional<Employee> empData=empReposiory.findById(id);
		if(empData.isPresent()) {
			return new ResponseEntity<>(empData.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable long id){
		try {
			empReposiory.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee emp){
		Optional<Employee> empdta=empReposiory.findById(id);
		if(empdta.isPresent()) {
			Employee e=empdta.get();
			e.setFirstName(emp.getFirstName());
			e.setLastName(emp.getLastName());
			e.setEmailId(emp.getEmailId());
			return new ResponseEntity<>(empReposiory.save(e),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

