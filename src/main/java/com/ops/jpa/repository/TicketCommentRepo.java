package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.TicketComment;

public interface TicketCommentRepo extends JpaRepository<TicketComment, Long> {

	public List<TicketComment> findByTicketId(Long ticketId);

}
