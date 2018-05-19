package com.ops.jpa.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ops.jpa.entities.Site;

public interface SiteRepo extends JpaRepository<Site, Long> {


	@Query("from Site s order by s.createdDate desc")
	public List<Site> findAllSites();

	@Query("SELECT s FROM Site s WHERE s.siteId IN :ids order by s.siteName")
	public List<Site> findUserAccessSites(@Param("ids") Set<Long> ids);

   

}
