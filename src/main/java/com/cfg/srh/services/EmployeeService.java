package com.cfg.srh.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cfg.srh.entities.EmployeeEntity;
import com.cfg.srh.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;

	public Employee fetchEmployeeTicketsById(int id) {
		Optional<EmployeeEntity> entityopt = employeeRepo.findById(id);
		if (entityopt.isPresent()) {
			EmployeeEntity e = entityopt.get();
			Employee emp = new Employee();
			emp.setEmployeeId(e.getEmployeeId());
			emp.setName(e.getName());
			emp.setTickets(e.getTickets());
			return emp;
		} else {
			return null;
		}
	}

}
