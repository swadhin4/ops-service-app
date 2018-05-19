package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.SiteSubMeter;

public interface SiteSubmeterRepo extends JpaRepository<SiteSubMeter, Long> {

	List<SiteSubMeter> findBySiteSiteId(Long siteId);

}
