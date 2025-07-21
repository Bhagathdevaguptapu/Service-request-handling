package com.cfg.srh.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfg.srh.dto.AdminDTO;
import com.cfg.srh.dto.EmployeeDTO;
import com.cfg.srh.entities.AdminEntity;
import com.cfg.srh.entities.EmployeeEntity;
import com.cfg.srh.repository.AdminRepository;
import com.cfg.srh.repository.EmployeeRepository;

@Service
public class LoginService {

	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	public String loginAdmin(String email, String password) {
		String stremail = null;
		String strpassword = null;

		Optional<AdminEntity> adminentity = adminRepo.findByEmail(email);

		if (adminentity.isPresent()) {
			AdminEntity a = adminentity.get();
			AdminDTO admin = new AdminDTO();
			admin.setEmail(a.getEmail());
			stremail = admin.getEmail();

			admin.setPassword(a.getPassword());
			strpassword = admin.getPassword();
		} else {
			return "AdminDTO not there in the database";
		}

		if (stremail.equalsIgnoreCase(email) && strpassword.equals(password)) {
			return "AdminDTO login successfully";
		} else {
			return "AdminDTO login is failed";
		}
	}

	public String employeeLogin(String email, String password) {
		String empemail = null;
		String emppass = null;
		Optional<EmployeeEntity> empentity = employeeRepo.findByEmail(email);
		if (empentity.isPresent()) {
			EmployeeEntity e = empentity.get();
			EmployeeDTO emp = new EmployeeDTO();
			emp.setEmail(e.getEmail());
			empemail = emp.getEmail();
			emp.setPassword(e.getPassword());
			emppass = emp.getPassword();
		} else
			return "Employee not there in the database";

		if (empemail.equalsIgnoreCase(email) && emppass.equalsIgnoreCase(password)) {
			return "Employee login successfully";
		} else
			return "Employee login is failed";
	}

}