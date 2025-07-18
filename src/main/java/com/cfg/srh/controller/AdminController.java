package com.cfg.srh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cfg.srh.services.EmployeeBO;
import com.cfg.srh.services.EmployeeService;

@RestController
public class AdminController {

	@Autowired
	private EmployeeService employeeservice;

	@GetMapping("/employee/tickets/{id}")
	public EmployeeBO fetchEmployeeTicketsById(@PathVariable("id") Integer id) {
		return employeeservice.fetchEmployeeTicketsById(id);
	}

}
