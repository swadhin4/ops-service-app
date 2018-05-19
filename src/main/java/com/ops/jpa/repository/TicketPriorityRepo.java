package com.ops.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.TicketPriority;

public interface TicketPriorityRepo extends JpaRepository<TicketPriority, Long> {

}
