package com.ops.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.Region;


public interface RegionRepo extends JpaRepository<Region, Long> {

}
