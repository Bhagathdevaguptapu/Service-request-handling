package com.cfg.srh.entities;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "srh", name = "ticketfeedback")
public class TicketFeedback {

	@Id
	private Integer feedbackId;
	@OneToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;
	@ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonBackReference
	private EmployeeEntity employee;
	private String feedbackText;
	private Timestamp givenAt;
}
