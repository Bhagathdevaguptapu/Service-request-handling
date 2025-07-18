package com.cfg.srh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfg.srh.services.LoginRequest;
import com.cfg.srh.services.LoginService;

@RestController
public class EmployeeContoller {
	@Autowired
	private LoginService loginservice;
	
	@PostMapping("/employee/login")
    public String employeeLogin(@RequestBody LoginRequest loginRequest) {
        return loginservice.employeeLogin(loginRequest.getEmail(), loginRequest.getPassword());
    }
	
	

}
