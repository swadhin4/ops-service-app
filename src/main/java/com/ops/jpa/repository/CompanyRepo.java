package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ops.jpa.entities.Company;



public interface CompanyRepo extends JpaRepository<Company, Long>{

	@Query("from Company c")
	List<Company> findCompanyByType();

	@Query("from Company c")
	List<Company> findAssetServiceProvider();


	@Query(value="select c.company_id, c.company_name,count(ct.ticket_number) as tkt_count from pm_cust_ticket ct"
			+ " left join pm_company c on c.company_id=ct.assigned_to"
			+ " where not ct.status = 9 and is_escalated =1"
			+" group by c.company_id,c.company_name",nativeQuery = true)
	public List<Object[]> findTicketEscalatedCount();


	@Query(value="select  c.company_id, c.company_name,ct.priority,count(ct.ticket_number) as tkt_countfrom from pm_cust_ticket ct"
			+ " left join pm_company c on c.company_id=ct.assigned_to"
			+ " where not ct.status = 9"
			+ " group by c.company_id,c.company_name,ct.priority",nativeQuery = true)
	public List<Object[]> findTicketPriorityCount();

}
