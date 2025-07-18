package com.cfg.srh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cfg.srh.dto.CloseTicketDTO;
import com.cfg.srh.dto.CommentDTO;
import com.cfg.srh.dto.UpdateStatusDTO;
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

	@PutMapping("/ticket/status")
	public String updateTicketStatus(@RequestBody UpdateStatusDTO dto) {
		return departmentService.updateTicketStatus(dto);
	}

	@PostMapping("/ticket/comment")
	public String addComment(@RequestBody CommentDTO dto) {
		return departmentService.addComment(dto);
	}

	@PutMapping("/ticket/close")
	public String closeTicket(@RequestBody CloseTicketDTO dto) {
		return departmentService.closeTicket(dto);
	}

}