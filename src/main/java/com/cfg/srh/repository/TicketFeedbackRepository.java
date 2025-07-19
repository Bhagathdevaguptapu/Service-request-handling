package com.cfg.srh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cfg.srh.entities.TicketFeedback;

@Repository
public interface TicketFeedbackRepository  extends JpaRepository<TicketFeedback, Integer>{

}
