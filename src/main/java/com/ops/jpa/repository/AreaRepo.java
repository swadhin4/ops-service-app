package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ops.jpa.entities.Area;



public interface AreaRepo extends JpaRepository<Area, Long>{

	@Query("from Area a where a.district.districtId=:districtId")
	public List<Area> findAreaBy(@Param(value="districtId") Long districtId);

}
