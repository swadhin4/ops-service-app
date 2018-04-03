package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.TicketAttachment;

public interface TicketAttachmentRepo extends JpaRepository<TicketAttachment, Long> {

	public List<TicketAttachment> findByTicketNumber(String ticketNumber);
	
	
	public List<TicketAttachment> findByAttachmentIdIn(List<Long> attachementIds);
}
