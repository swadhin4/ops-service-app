package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.ViewSitePriority;

public interface ViewSitePriorityRepo extends JpaRepository<ViewSitePriority, Long> {

	public List<ViewSitePriority> findBySiteIdIn(List<Long> siteId);
}
