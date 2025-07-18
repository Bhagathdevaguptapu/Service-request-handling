package com.cfg.srh.services;

import lombok.Data;

@Data
public class CancelTicketRequestDTO {
	private Integer ticketId;
    private String cancelReason;

}
