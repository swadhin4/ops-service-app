package com.ops.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.TicketPrioritySettings;

public interface TicketPrioritySettingsRepo extends JpaRepository<TicketPrioritySettings, Long> {
	
	
	public TicketPrioritySettings findByTicketCategoryId(Long categoryId);
}
