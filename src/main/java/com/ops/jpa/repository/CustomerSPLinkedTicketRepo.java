package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.CustomerSPLinkedTicket;

public interface CustomerSPLinkedTicketRepo extends JpaRepository<CustomerSPLinkedTicket, Long> {

	public List<CustomerSPLinkedTicket> findByCustTicketIdAndDelFlag(Long custTicketId, int delFlag);

}
