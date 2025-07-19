package com.cfg.srh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cfg.srh.constants.TicketStatusConstants;
import com.cfg.srh.dto.CloseTicketDTO;
import com.cfg.srh.dto.CommentDTO;
import com.cfg.srh.dto.UpdateStatusDTO;
import com.cfg.srh.entities.Department;
import com.cfg.srh.entities.Ticket;
import com.cfg.srh.entities.TicketComment;
import com.cfg.srh.entities.TicketStatusHistory;
import com.cfg.srh.exceptions.InvalidStatusException;
import com.cfg.srh.repository.DepartmentRepository;
import com.cfg.srh.repository.TicketCommentRepository;
import com.cfg.srh.repository.TicketRepository;
import com.cfg.srh.repository.TicketStatusHistoryRepository;
import com.cfg.srh.services.DepartmentService;

@SpringBootTest
public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketCommentRepository commentRepo;

    @Mock
    private TicketStatusHistoryRepository statusHistoryRepo;

    private Ticket ticket;
    private Department department;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setStatus("OPEN");

        department = new Department();
        department.setDepartmentId(101);
        department.setAssignedTickets(List.of(ticket));
    }

    @Test
    public void testViewAssignedTicketsById_Found() {
        when(departmentRepository.findById(101)).thenReturn(Optional.of(department));

        List<Ticket> result = departmentService.viewAssignedTicketsByID(101);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testViewAssignedTicketsById_NotFound() {
        when(departmentRepository.findById(999)).thenReturn(Optional.empty());

        List<Ticket> result = departmentService.viewAssignedTicketsByID(999);
        assertNull(result);
    }

    @Test
    public void testAcceptTicketByID_Found() {
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        String response = departmentService.acceptTicketByID(1);

        assertEquals(TicketStatusConstants.STARTED, ticket.getStatus());
        verify(ticketRepository).save(ticket);
        verify(statusHistoryRepo).save(any(TicketStatusHistory.class));
        assertTrue(response.contains("STARTED"));
    }

    @Test
    public void testAcceptTicketByID_NotFound() {
        when(ticketRepository.findById(999)).thenReturn(Optional.empty());

        String response = departmentService.acceptTicketByID(999);
        assertEquals("Ticket not found", response);
    }

    @Test
    public void testUpdateTicketStatus_Success() throws InvalidStatusException {
        UpdateStatusDTO dto = new UpdateStatusDTO();
        dto.setTicketId(1);
        dto.setStatus("RESOLVED");

        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        String result = departmentService.updateTicketStatus(dto);

        assertEquals("RESOLVED", ticket.getStatus());
        verify(ticketRepository).save(ticket);
        verify(statusHistoryRepo).save(any(TicketStatusHistory.class));
        assertTrue(result.contains("RESOLVED"));
    }

    @Test
    public void testUpdateTicketStatus_TicketNotFound() throws InvalidStatusException {
        UpdateStatusDTO dto = new UpdateStatusDTO();
        dto.setTicketId(999);
        dto.setStatus("IN_PROGRESS");

        when(ticketRepository.findById(999)).thenReturn(Optional.empty());

        String result = departmentService.updateTicketStatus(dto);
        assertEquals("Ticket not found", result);
    }

    @Test
    public void testUpdateTicketStatus_InvalidStatus() {
        UpdateStatusDTO dto = new UpdateStatusDTO();
        dto.setTicketId(1);
        dto.setStatus("   "); // blank

        InvalidStatusException ex = assertThrows(InvalidStatusException.class, () -> {
            departmentService.updateTicketStatus(dto);
        });
        assertEquals("Status cannot be null or empty.", ex.getMessage());
    }

    @Test
    public void testAddComment_Success() {
        CommentDTO dto = new CommentDTO();
        dto.setTicketId(1);
        dto.setCommentText("Looks good");
        dto.setCommenterName("Dept Lead");

        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        String result = departmentService.addComment(dto);

        verify(commentRepo).save(any(TicketComment.class));
        assertEquals("Comment added successfully", result);
    }

    @Test
    public void testAddComment_TicketNotFound() {
        CommentDTO dto = new CommentDTO();
        dto.setTicketId(999);
        dto.setCommentText("Not valid");
        dto.setCommenterName("Test");

        when(ticketRepository.findById(999)).thenReturn(Optional.empty());

        String result = departmentService.addComment(dto);
        assertEquals("Ticket not found", result);
    }

    @Test
    public void testCloseTicket_Success() {
        CloseTicketDTO dto = new CloseTicketDTO();
        dto.setTicketId(1);
        dto.setReason("Issue resolved");

        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        String result = departmentService.closeTicket(dto);

        assertEquals(TicketStatusConstants.CLOSED, ticket.getStatus());
        assertEquals("Issue resolved", ticket.getCloseReason());
        verify(ticketRepository).save(ticket);
        verify(statusHistoryRepo).save(any(TicketStatusHistory.class));
        assertEquals("Ticket closed successfully", result);
    }

    @Test
    public void testCloseTicket_TicketNotFound() {
        CloseTicketDTO dto = new CloseTicketDTO();
        dto.setTicketId(999);
        dto.setReason("Invalid ID");

        when(ticketRepository.findById(999)).thenReturn(Optional.empty());

        String result = departmentService.closeTicket(dto);
        assertEquals("Ticket not found", result);
    }
}
