package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ops.jpa.entities.TicketPriorityCountView;

public interface TicketPriorityCountViewRepo extends JpaRepository<TicketPriorityCountView, Long> {



	@Query(value="select s.site_id AS site_id,s.site_code AS site_code,s.site_name AS site_name,ct.priority AS priority, count(ct.ticket_number) AS tkt_countfrom" 
			+" from (pm_cust_ticket ct left join pm_site s on((s.site_id = ct.site_id)))" 
			+" where (ct.status <> 9) group by s.site_code,s.site_name,ct.priority",nativeQuery=true )

	public List<Object[]> listPriorityTIckets();


}
