package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.SiteSalesOperation;

public interface SiteSalesOperationRepo extends JpaRepository<SiteSalesOperation, Long> {

	List<SiteSalesOperation> findBySiteSiteId(Long siteId);

}
