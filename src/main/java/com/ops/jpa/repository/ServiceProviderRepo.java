package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ops.jpa.entities.ServiceProvider;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Long> {

	@Query("from ServiceProvider sp where sp.email=:email and sp.company.companyId=:customerId")
	public ServiceProvider findByEmailAndCompanyCompanyId(@Param(value="email") String email, @Param(value="customerId") Long customerId);

	@Query("from ServiceProvider sp where sp.company.companyId=:customerId")
	public List<ServiceProvider> findByCompany(@Param(value="customerId") Long customerId);

	@Query("from ServiceProvider sp where sp.company.companyId in :customerId order by sp.name")
	public List<ServiceProvider> findByCompanyCompanyIdIn(@Param("customerId") List<Long> companyId);
	
	public ServiceProvider findBySpUsername(String username);

	@Query(value="select sp_username from pm_service_provider sp where sp_username = :uniqueUser", nativeQuery=true)
	public String validateSPUser(@Param(value="uniqueUser") String uniqueUser);
	
}
