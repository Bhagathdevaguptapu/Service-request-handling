package com.cfg.srh.dto;

import java.util.List;

import com.cfg.srh.entities.Ticket;

import lombok.Data;

@Data
public class Employee {
	private Integer employeeId;
	private String name;
	private List<Ticket> tickets;
}
