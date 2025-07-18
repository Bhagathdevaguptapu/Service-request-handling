package com.cfg.srh.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "srh", name = "admin")

public class AdminEntity {

	@Id
	private Integer adminId;

	private String name;

	private String email;

	private String password;
	
	
	
}
