package com.cfg.srh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfg.srh.services.CancelTicketRequestDTO;
import com.cfg.srh.services.EmployeeDTO;
import com.cfg.srh.services.EmployeeService;
import com.cfg.srh.services.LoginRequest;
import com.cfg.srh.services.LoginService;

@RestController
public class AdminController {

	@Autowired
	private LoginService loginservice;

	@Autowired
	private EmployeeService employeeservice;

	@PostMapping("/admin/login")
	public String adminLogin(@RequestBody LoginRequest loginRequest) {
		return loginservice.loginAdmin(loginRequest.getEmail(), loginRequest.getPassword());
	}

	@GetMapping("/employee/tickets/{id}")
	public EmployeeDTO getEmployeeTickets(@PathVariable int id) {
		return employeeservice.fetchEmployeeTicketsById(id);
	}

	@GetMapping("/employees/tickets")
	public List<EmployeeDTO> getAllEmployeeTickets() {
		return employeeservice.fetchAllEmployeeTickets();
	}

	@PostMapping("/ticket/cancel")
	public String cancelTicket(@RequestBody CancelTicketRequestDTO ticketRequest) {
		employeeservice.cancelTicketById(ticketRequest.getTicketId(), ticketRequest.getCancelReason());
		return "Ticket with ID " + ticketRequest.getTicketId() + " has been cancelled.";
	}
	
//	public String assigntickettodepartment{
//		
//	}

}
