package com.cfg.srh.services;

import java.util.List;

import com.cfg.srh.entities.Ticket;

import lombok.Data;


@Data
public class EmployeeDTO {
	
	private Integer employeeId;

	private String name;

	private String email;

	private String password;
	
	private List<Ticket> tickets;

}
