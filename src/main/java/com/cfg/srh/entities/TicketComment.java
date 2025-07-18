package com.cfg.srh.entities;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "srh", name = "ticketcomment")
public class TicketComment {

	@Id
	private Integer commentId;

	@ManyToOne
	@JoinColumn(name = "ticket_id")
	@JsonBackReference("ticket-comments")
	private Ticket ticket;


	private String commenterRole; // ADMIN, IT, etc.
	private String commenterName;

	private String commentText;
	private Timestamp commentedAt;
}
