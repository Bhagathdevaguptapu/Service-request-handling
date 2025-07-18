package com.cfg.srh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cfg.srh.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
