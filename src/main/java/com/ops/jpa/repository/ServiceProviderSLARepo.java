package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.ServiceProviderSLADetails;

public interface ServiceProviderSLARepo extends JpaRepository<ServiceProviderSLADetails, Long> {

	public List<ServiceProviderSLADetails> findByServiceProviderServiceProviderId(Long spId);
	
	public ServiceProviderSLADetails findByServiceProviderServiceProviderIdAndPriorityId(Long spId, Long priorityId);

}
