package com.ops.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.SlaApproachingView;

public interface SlaApproachingRepo extends JpaRepository<SlaApproachingView, Long> {

}
