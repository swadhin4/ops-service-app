package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.SPEscalationLevels;

public interface SPEscalationLevelRepo extends JpaRepository<SPEscalationLevels, Long> {

	List<SPEscalationLevels> findByServiceProviderServiceProviderId(Long serviceProviderId);

}
