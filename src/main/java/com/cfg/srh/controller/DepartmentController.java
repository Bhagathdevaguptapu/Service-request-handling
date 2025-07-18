package com.cfg.srh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cfg.srh.entities.Ticket;
import com.cfg.srh.services.DepartmentService;

@RestController
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/tickets/{departmentId}")
    public List<Ticket> viewAssignedTickets(@PathVariable Integer departmentId) {
		System.out.println("Called /api/tickets/" + departmentId);
        return departmentService.viewAssignedTicketsByID(departmentId);
    }

    @PutMapping("/ticket/accept/{ticketId}")
    public String acceptTicket(@PathVariable Integer ticketId) {
        return departmentService.acceptTicketByID(ticketId);
    }

    @PutMapping("/ticket/status/{ticketId}")
    public String updateTicketStatus(@PathVariable Integer ticketId,
                                     @RequestParam String status) {
        return departmentService.updateTicketStatus(ticketId, status);
    }

    @PostMapping("/ticket/comment/{ticketId}")
    public String addComment(@PathVariable Integer ticketId,
                             @RequestParam String commenterName,
                             @RequestParam String commentText) {
        return departmentService.addComment(ticketId, commenterName, commentText);
    }

    @PutMapping("/ticket/close/{ticketId}")
    public String closeTicket(@PathVariable Integer ticketId,
                              @RequestParam String reason) {
        return departmentService.closeTicket(ticketId, reason);
    }
}
