package com.cfg.srh.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data

@Table(schema = "srh", name = "ticketstatushistory")
public class TicketStatusHistory {

	@Id
	private Integer historyId;

	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;

	private String status; // RAISED, ASSIGNED, etc.
	private String updatedBy;

	private Timestamp updatedAt;
}
