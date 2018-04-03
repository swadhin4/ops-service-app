package com.ops.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.TicketSiteView;

public interface TicketViewRepo extends JpaRepository<TicketSiteView, Long> {

}
