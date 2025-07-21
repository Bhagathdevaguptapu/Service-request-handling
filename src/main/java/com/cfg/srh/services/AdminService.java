package com.cfg.srh.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cfg.srh.controller.ResponseData;
import com.cfg.srh.dto.EmployeeDTO;
import com.cfg.srh.dto.LoginRequest;
import com.cfg.srh.entities.Department;
import com.cfg.srh.entities.EmployeeEntity;
import com.cfg.srh.entities.Ticket;
import com.cfg.srh.exceptions.InvalidDepartmentException;
import com.cfg.srh.repository.DepartmentRepository;
import com.cfg.srh.repository.EmployeeRepository;
import com.cfg.srh.repository.TicketRepository;

@Service
public class AdminService {

	@Autowired
	public TicketRepository ticketrepo;

	@Autowired
	public DepartmentRepository departmentrepo;

	@Autowired
	public EmployeeRepository employeeRepo;
	

	public EmployeeDTO fetchEmployeeTicketsById(int id) {
		Optional<EmployeeEntity> entityopt = employeeRepo.findById(id);
		if (entityopt.isPresent()) {
			EmployeeEntity e = entityopt.get();
			EmployeeDTO emp = new EmployeeDTO();
			emp.setEmployeeId(e.getEmployeeId());
			emp.setName(e.getName());
			emp.setTickets(e.getTickets());
			return emp;
		} else {
			return null;
		}
	}

	public List<EmployeeDTO> fetchAllEmployeeTickets() {
		List<EmployeeEntity> empentity = employeeRepo.findAll();
		return empentity.stream().map(e -> {
			EmployeeDTO emp = new EmployeeDTO();
			emp.setEmployeeId(e.getEmployeeId());
			emp.setName(e.getName());
			emp.setTickets(e.getTickets());
			return emp;
		}).collect(Collectors.toList());
	}

	public void cancelTicketById(Integer ticketId, String cancelReason) {
		Optional<Ticket> ticketOpt = ticketrepo.findById(ticketId);
		if (ticketOpt.isEmpty()) {
			throw new RuntimeException("Ticket not found with id: " + ticketId);
		}
		Ticket ticket = ticketOpt.get();
		ticket.setStatus("Cancelled"); // Use a meaningful status
		ticket.setCancelReason(cancelReason);
		ticketrepo.save(ticket);
	}

	public String assignTicketToDepartment(int ticketId, int departmentId) throws InvalidDepartmentException {
	    Optional<Ticket> ticketOpt = ticketrepo.findById(ticketId);
	    Optional<Department> departmentOpt = departmentrepo.findById(departmentId);

	    if (!ticketOpt.isPresent()) {
	        throw new InvalidDepartmentException("Ticket with ID " + ticketId + " not found.");
	    }

	    if (!departmentOpt.isPresent()) {
	        throw new InvalidDepartmentException("Department with ID " + departmentId + " not found.");
	    }

	    Ticket ticket = ticketOpt.get();
	    Department department = departmentOpt.get();

	    ticket.setAssignedToDepartment(department);
	    ticket.setStatus("ASSIGNED");
	    ticket.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

	    ticketrepo.save(ticket);

	    return "Ticket ID " + ticketId + " successfully assigned to Department ID " + departmentId;
	}

}
