package com.cfg.srh.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfg.srh.dto.EmployeeDTO;
import com.cfg.srh.dto.RaiseTicketDTO;
import com.cfg.srh.entities.EmployeeEntity;
import com.cfg.srh.entities.Ticket;
import com.cfg.srh.entities.TicketFeedback;
import com.cfg.srh.exceptions.InvalidTicketException;
import com.cfg.srh.repository.EmployeeRepository;
import com.cfg.srh.repository.TicketFeedbackRepository;
import com.cfg.srh.repository.TicketRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private TicketFeedbackRepository feedbackRepo;

	
	public void riseTicket(RaiseTicketDTO dto) throws InvalidTicketException {
	    if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
	        throw new InvalidTicketException("Title can't be null");
	    }

	    Optional<EmployeeEntity> empOpt = employeeRepo.findById(dto.getEmployeeId());
	    if (empOpt.isPresent()) {
	        Ticket ticket = new Ticket();
	        ticket.setTitle(dto.getTitle());
	        ticket.setDescription(dto.getDescription());
	        ticket.setStatus("RAISED");
	        ticket.setCreatedBy(empOpt.get());
	        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));

	        ticketRepo.save(ticket);
	    }
	}


	
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


	public void cancleMyTicket(int ticketId) throws InvalidTicketException {
	    Optional<Ticket> ticketOpt = ticketRepo.findById(ticketId);
	    if (ticketOpt.isPresent()) {
	        Ticket ticket = ticketOpt.get();
	        ticket.setStatus("CANCELLED");
	        ticket.setCancelReason("Cancelled by employee");
	        ticket.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	        ticketRepo.save(ticket);
	    } else {
	        throw new InvalidTicketException("Invalid ticket ID: " + ticketId);
	    }
	}


	
	public void giveFeedback(int ticketId, String feedbackText) {
		Optional<Ticket> ticketOpt = ticketRepo.findById(ticketId);
		if (ticketOpt.isPresent()) {
			Ticket ticket = ticketOpt.get();

			if (ticket.getFeedback() == null) {
				TicketFeedback feedback = new TicketFeedback();
				feedback.setTicket(ticket);
				feedback.setEmployee(ticket.getCreatedBy());
				feedback.setFeedbackText(feedbackText);
				feedback.setGivenAt(new Timestamp(System.currentTimeMillis()));

				feedbackRepo.save(feedback);
			}
		}
	}
	
	
	
	
}
