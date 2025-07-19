package com.cfg.srh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cfg.srh.dto.EmployeeDTO;
import com.cfg.srh.dto.RaiseTicketDTO;
import com.cfg.srh.entities.EmployeeEntity;
import com.cfg.srh.entities.Ticket;
import com.cfg.srh.entities.TicketFeedback;
import com.cfg.srh.exceptions.InvalidTicketException;
import com.cfg.srh.repository.EmployeeRepository;
import com.cfg.srh.repository.TicketFeedbackRepository;
import com.cfg.srh.repository.TicketRepository;
import com.cfg.srh.services.EmployeeService;

@SpringBootTest
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private TicketRepository ticketRepo;

    @Mock
    private TicketFeedbackRepository feedbackRepo;

    private EmployeeEntity employee;
    private Ticket ticket;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        employee = new EmployeeEntity();
        employee.setEmployeeId(101);
        employee.setName("Lahari");
        employee.setTickets(new ArrayList<>());

        ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setTitle("Issue A");
        ticket.setStatus("OPEN");
        ticket.setCreatedBy(employee);
    }

    @Test
    public void testRiseTicket_Success() throws InvalidTicketException {
        RaiseTicketDTO dto = new RaiseTicketDTO();
        dto.setTitle("Network issue");
        dto.setDescription("Wi-Fi not working");
        dto.setEmployeeId(101);

        when(employeeRepo.findById(101)).thenReturn(Optional.of(employee));

        employeeService.riseTicket(dto);

        verify(ticketRepo).save(any(Ticket.class));
    }

    @Test
    public void testRiseTicket_MissingTitle() {
        RaiseTicketDTO dto = new RaiseTicketDTO();
        dto.setTitle("   "); // Blank
        dto.setEmployeeId(101);

        InvalidTicketException ex = assertThrows(InvalidTicketException.class, () -> {
            employeeService.riseTicket(dto);
        });

        assertEquals("Title can't be null", ex.getMessage());
    }

    @Test
    public void testFetchEmployeeTicketsById_Found() {
        employee.setTickets(List.of(ticket));

        when(employeeRepo.findById(101)).thenReturn(Optional.of(employee));

        EmployeeDTO dto = employeeService.fetchEmployeeTicketsById(101);
        assertNotNull(dto);
        assertEquals("Lahari", dto.getName());
        assertEquals(1, dto.getTickets().size());
    }

    @Test
    public void testFetchEmployeeTicketsById_NotFound() {
        when(employeeRepo.findById(999)).thenReturn(Optional.empty());

        EmployeeDTO dto = employeeService.fetchEmployeeTicketsById(999);
        assertNull(dto);
    }

    @Test
    public void testCancleMyTicket_Success() throws InvalidTicketException {
        when(ticketRepo.findById(1)).thenReturn(Optional.of(ticket));

        employeeService.cancleMyTicket(1);

        assertEquals("CANCELLED", ticket.getStatus());
        assertEquals("Cancelled by employee", ticket.getCancelReason());
        verify(ticketRepo).save(ticket);
    }

    @Test
    public void testCancleMyTicket_InvalidId() {
        when(ticketRepo.findById(999)).thenReturn(Optional.empty());

        InvalidTicketException ex = assertThrows(InvalidTicketException.class, () -> {
            employeeService.cancleMyTicket(999);
        });

        assertEquals("Invalid ticket ID: 999", ex.getMessage());
    }

    @Test
    public void testGiveFeedback_Success_NoExistingFeedback() {
        ticket.setFeedback(null);

        when(ticketRepo.findById(1)).thenReturn(Optional.of(ticket));

        employeeService.giveFeedback(1, "Great support!");

        verify(feedbackRepo).save(any(TicketFeedback.class));
    }

    @Test
    public void testGiveFeedback_AlreadyGiven() {
        TicketFeedback existing = new TicketFeedback();
        existing.setFeedbackText("Old feedback");
        ticket.setFeedback(existing);

        when(ticketRepo.findById(1)).thenReturn(Optional.of(ticket));

        employeeService.giveFeedback(1, "New feedback");

        verify(feedbackRepo, never()).save(any());
    }

    @Test
    public void testGiveFeedback_TicketNotFound() {
        when(ticketRepo.findById(999)).thenReturn(Optional.empty());

        // Should not throw exception
        employeeService.giveFeedback(999, "No ticket");

        verify(feedbackRepo, never()).save(any());
    }
}
