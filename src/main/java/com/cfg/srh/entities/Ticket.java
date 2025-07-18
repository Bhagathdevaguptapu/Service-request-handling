package com.cfg.srh.entities;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

	@ManyToOne
	@JoinColumn(name = "created_by")
	@JsonBackReference
	private EmployeeEntity createdBy;
	

	@ManyToOne
	@JoinColumn(name = "assigned_to_department")
	@JsonBackReference
	private Department assignedToDepartment;

	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Comments, feedback, status history
	@OneToMany(mappedBy = "ticket")
	@JsonBackReference
	private List<TicketComment> comments;

	@OneToMany(mappedBy = "ticket")
	@JsonBackReference
	private List<TicketStatusHistory> statusHistory;

	@OneToOne(mappedBy = "ticket")
	@JsonBackReference
	private TicketFeedback feedback;
}
