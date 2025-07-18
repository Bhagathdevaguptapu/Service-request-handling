package com.cfg.srh.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "srh", name = "employee")
public class EmployeeEntity {

	@Id
	private Integer employeeId;

	private String name;
	private String email;
	private String password;

	// Relationships
	@OneToMany(mappedBy = "createdBy")
	@JsonBackReference
	private List<Ticket> tickets;
}
