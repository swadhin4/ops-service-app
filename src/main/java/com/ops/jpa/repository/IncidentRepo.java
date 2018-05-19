package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.IncidentReport;

public interface IncidentRepo extends JpaRepository<IncidentReport, Long> {

	public List<IncidentReport> findBySiteIdIn(List<Long> siteIds);

}
