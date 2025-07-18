package com.cfg.srh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfg.srh.repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminrepository;
	
	public void viewAllRequestsByEmployees()
	{
		
	}

}
