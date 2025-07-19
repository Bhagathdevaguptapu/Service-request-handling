package com.cfg.srh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cfg.srh.entities.AdminEntity;
import com.cfg.srh.entities.EmployeeEntity;
import com.cfg.srh.repository.AdminRepository;
import com.cfg.srh.repository.EmployeeRepository;
import com.cfg.srh.services.LoginService;

@SpringBootTest
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private AdminRepository adminRepo;

    @Mock
    private EmployeeRepository employeeRepo;

    private AdminEntity admin;
    private EmployeeEntity employee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        admin = new AdminEntity();
        admin.setEmail("admin@example.com");
        admin.setPassword("admin123");

        employee = new EmployeeEntity();
        employee.setEmail("employee@example.com");
        employee.setPassword("emp123");
    }

    // --- Admin Login Tests ---

    @Test
    public void testAdminLogin_Success() {
        when(adminRepo.findByEmail("admin@example.com")).thenReturn(Optional.of(admin));

        String result = loginService.loginAdmin("admin@example.com", "admin123");

        assertEquals("AdminDTO login successfully", result);
    }

    @Test
    public void testAdminLogin_Failure_WrongPassword() {
        when(adminRepo.findByEmail("admin@example.com")).thenReturn(Optional.of(admin));

        String result = loginService.loginAdmin("admin@example.com", "wrongpass");

        assertEquals("AdminDTO login is failed", result);
    }

    @Test
    public void testAdminLogin_Failure_NotFound() {
        when(adminRepo.findByEmail("noadmin@example.com")).thenReturn(Optional.empty());

        String result = loginService.loginAdmin("noadmin@example.com", "admin123");

        assertEquals("AdminDTO not there in the database", result);
    }

    // --- Employee Login Tests ---

    @Test
    public void testEmployeeLogin_Success() {
        when(employeeRepo.findByEmail("employee@example.com")).thenReturn(Optional.of(employee));

        String result = loginService.employeeLogin("employee@example.com", "emp123");

        assertEquals("Employee login successfully", result);
    }

    @Test
    public void testEmployeeLogin_Failure_WrongPassword() {
        when(employeeRepo.findByEmail("employee@example.com")).thenReturn(Optional.of(employee));

        String result = loginService.employeeLogin("employee@example.com", "wrongpass");

        assertEquals("Employee login is failed", result);
    }

    @Test
    public void testEmployeeLogin_Failure_NotFound() {
        when(employeeRepo.findByEmail("nouser@example.com")).thenReturn(Optional.empty());

        String result = loginService.employeeLogin("nouser@example.com", "emp123");

        assertEquals("Employee not there in the database", result);
    }
}
