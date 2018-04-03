package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ops.jpa.entities.TicketEscalationCountView;

public interface TicketEscalationCountViewRepo extends JpaRepository<TicketEscalationCountView, Long> {


	@Query(value="select s.site_id,s.site_code,s.site_name,count(ct.ticket_number) as tkt_count from pm_cust_ticket ct"
			+" left join pm_site s on s.site_id=ct.site_id"
			+ " where not ct.status = 9 and is_escalated =1"
			+ " group by s.site_code,s.site_name",nativeQuery = true)
	public List<Object[]> findTicketEscalatedCount();
}
