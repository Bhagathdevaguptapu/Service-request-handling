package com.cfg.srh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfg.srh.dto.EmployeeDTO;
import com.cfg.srh.dto.FeedbackDTO;
import com.cfg.srh.dto.LoginRequest;
import com.cfg.srh.dto.RaiseTicketDTO;
import com.cfg.srh.exceptions.InvalidTicketException;
import com.cfg.srh.services.EmployeeService;
import com.cfg.srh.services.LoginService;

@RestController
public class EmployeeContoller {

    @Autowired
    private EmployeeService service;

    @Autowired
    private LoginService loginservice;

    @PostMapping("/raiseTicket")
    public ResponseData raiseTicket(@RequestBody RaiseTicketDTO dto) {
        ResponseData response = new ResponseData();
        try {
            service.riseTicket(dto);
            response.setStatus("success");
            response.setMessage("Ticket raised successfully.");
        } catch (InvalidTicketException ex) {
            response.setStatus("failed");
            response.setMessage(ex.getMessage());  // e.g., "Title can't be null"
        }
        return response;
    }


    @GetMapping("/viewMyTickets/{id}")
    public ResponseData viewMyTickets(@PathVariable("id") int employeeId) {
        ResponseData response = new ResponseData();
        EmployeeDTO employee = service.fetchEmployeeTicketsById(employeeId);

        if (employee != null) {
            response.setStatus("success");
            response.setData(employee);
        } else {
            response.setStatus("failed");
            response.setMessage("No tickets found for the given employee.");
        }

        return response;
    }

    @PostMapping("/cancleMyTicket/{id}")
    public ResponseData cancleMyTicket(@PathVariable("id") int ticketId) {
        ResponseData response = new ResponseData();
        try {
            service.cancleMyTicket(ticketId);
            response.setStatus("success");
            response.setMessage("Ticket cancelled successfully.");
        } catch (InvalidTicketException e) {
            response.setStatus("failed");
            response.setMessage(e.getMessage());
        }
        return response;
    }


    @PostMapping("/giveFeedback")
    public ResponseData giveFeedback(@RequestBody FeedbackDTO feedback) {
        ResponseData response = new ResponseData();
        service.giveFeedback(feedback.getTicketId(), feedback.getFeedbackText());
        response.setStatus("success");
        response.setMessage("Feedback submitted successfully.");
        return response;
    }

    @PostMapping("/employee/login")
    public ResponseData employeeLogin(@RequestBody LoginRequest loginRequest) {
        ResponseData response = new ResponseData();
        String result = loginservice.employeeLogin(loginRequest.getEmail(), loginRequest.getPassword());

        if ("Login successful".equalsIgnoreCase(result)) {
            response.setStatus("success");
            response.setMessage(result);
        } else {
            response.setStatus("failed");
            response.setMessage(result);
        }

        return response;
    }
}
