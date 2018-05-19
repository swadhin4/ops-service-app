package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.SiteLicence;

public interface LicenseRepo extends JpaRepository<SiteLicence, Long> {

	public List<SiteLicence> findBySiteSiteId(Long siteId);
	
	public List<SiteLicence> findByLicenseIdIn(List<Long> licenseIds);

}
