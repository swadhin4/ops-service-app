package com.ops.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.TicketCategory;



public interface TicketCategoryRepo extends JpaRepository<TicketCategory, Long>{

	
}
