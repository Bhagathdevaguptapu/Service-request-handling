package com.cfg.srh.entities;


import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "srh", name = "ticket")
public class Ticket {

	@Id
	private Integer ticketId;

	private String title;
	private String description;

	private String status; // RAISED, ASSIGNED, STARTED, etc.

	private String cancelReason;
	private String closeReason;
	
	private Timestamp createdAt;  
	private Timestamp updatedAt;

	@ManyToOne
	@JoinColumn(name = "assigned_to_department")
	@JsonBackReference("dept-ticket")
	private Department assignedToDepartment;

	@ManyToOne
	@JoinColumn(name = "created_by")
	@JsonBackReference("employee-ticket")
	private EmployeeEntity createdBy;

	@OneToMany(mappedBy = "ticket")
	@JsonManagedReference("ticket-comments")
	private List<TicketComment> comments;

	@OneToMany(mappedBy = "ticket")
	@JsonManagedReference("ticket-history")
	private List<TicketStatusHistory> statusHistory;

	@OneToOne(mappedBy = "ticket")
	@JsonManagedReference("ticket-feedback")
	private TicketFeedback feedback;

}
