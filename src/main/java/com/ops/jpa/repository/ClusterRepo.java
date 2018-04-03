package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.Cluster;



public interface ClusterRepo extends JpaRepository<Cluster, Long>{


	public List<Cluster> findByDistrictIdAndArea(Long districtId, Long areaId);
}
