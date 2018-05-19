package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.ViewSiteStatus;

public interface ViewSiteStatusRepo extends JpaRepository<ViewSiteStatus, Long> {

	public List<ViewSiteStatus> findBySiteIdIn(List<Long> siteId);
}
