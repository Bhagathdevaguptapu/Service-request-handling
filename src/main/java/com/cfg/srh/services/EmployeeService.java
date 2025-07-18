package com.cfg.srh.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfg.srh.entities.EmployeeEntity;
import com.cfg.srh.entities.Ticket;
import com.cfg.srh.repository.EmployeeRepository;
import com.cfg.srh.repository.TicketRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private TicketRepository ticketrepo;

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
	
	public List<EmployeeDTO> fetchAllEmployeeTickets(){
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

}
