package com.cfg.srh.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfg.srh.constants.TicketStatusConstants;
import com.cfg.srh.entities.Department;
import com.cfg.srh.entities.Ticket;
import com.cfg.srh.entities.TicketComment;
import com.cfg.srh.entities.TicketStatusHistory;

import com.cfg.srh.repository.DepartmentRepository;
import com.cfg.srh.repository.TicketCommentRepository;
import com.cfg.srh.repository.TicketRepository;
import com.cfg.srh.repository.TicketStatusHistoryRepository;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private TicketCommentRepository commentRepo;
	
	@Autowired
	private TicketStatusHistoryRepository statusHistoryRepo;


	public List<Ticket> viewAssignedTicketsByID(Integer departmentId) {
        Optional<Department> dept = departmentRepository.findById(departmentId);
        return dept.map(Department::getAssignedTickets).orElse(null);
    }

    public String acceptTicketByID(Integer ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setStatus(TicketStatusConstants.STARTED);
            ticket.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            ticketRepository.save(ticket);
            saveStatusHistory(ticket, TicketStatusConstants.STARTED, "Department");
            return "Ticket accepted and marked as STARTED";
        }
        return "Ticket not found";
    }

    public String updateTicketStatus(Integer ticketId, String status) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setStatus(status);
            ticket.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            ticketRepository.save(ticket);
            saveStatusHistory(ticket, status, "Department");
            return "Status updated to " + status;
        }
        return "Ticket not found";
    }

    public String addComment(Integer ticketId, String commenterName, String commentText) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            TicketComment comment = new TicketComment();
            comment.setTicket(ticketOpt.get());
            comment.setCommentText(commentText);
            comment.setCommentedAt(new Timestamp(System.currentTimeMillis()));
            comment.setCommenterRole("DEPARTMENT");
            comment.setCommenterName(commenterName);
            commentRepo.save(comment);
            return "Comment added successfully";
        }
        return "Ticket not found";
    }

    public String closeTicket(Integer ticketId, String reason) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setStatus(TicketStatusConstants.CLOSED);
            ticket.setCloseReason(reason);
            ticket.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            ticketRepository.save(ticket);
            saveStatusHistory(ticket, TicketStatusConstants.CLOSED, "Department");
            return "Ticket closed successfully";
        }
        return "Ticket not found";
    }

	
	private void saveStatusHistory(Ticket ticket, String status, String updatedBy) {
	    TicketStatusHistory history = new TicketStatusHistory();
	    history.setTicket(ticket);
	    history.setStatus(status);
	    history.setUpdatedBy(updatedBy);
	    history.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	    statusHistoryRepo.save(history);
	}

	
}
