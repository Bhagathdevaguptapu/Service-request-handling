package com.cfg.srh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfg.srh.dto.Employee;
import com.cfg.srh.dto.FeedbackDTO;
import com.cfg.srh.dto.RaiseTicketDTO;
import com.cfg.srh.services.EmployeeService;

@RestController
public class EmployeeContoller {

	@Autowired
    private EmployeeService service;

    
    @PostMapping("/raiseTicket")
    public String raiseTicket(@RequestBody RaiseTicketDTO dto) {
        service.riseTicket(dto);
        return "Ticket raised successfully.";
    }

	@GetMapping("/viewMyTickets/{id}")
	public Employee viewMyTickets(@PathVariable("id") int employeeId) {
		return service.fetchEmployeeTicketsById(employeeId);
	}

	@PostMapping("/cancleMyTicket/{id}")
	public String cancleMyTicket(@PathVariable("id") int ticketId) {
		service.cancleMyTicket(ticketId);
		return "deleted";
	}

	

	@PostMapping("/giveFeedback")
	public String giveFeedback(@RequestBody FeedbackDTO feedback) {
		service.giveFeedback(feedback.getTicketId(), feedback.getFeedbackText());
		return "feedback submitted";
	}

}
